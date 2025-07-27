package Interfaces.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Prestamos {
    private JPanel panel1;
    private JTextField buscarTextField;
    private JTable tablePrestamos;
    private JButton agregarButton;
    private JButton marcarComoDevueltoButton;

    public Prestamos() {
        cargarTablaPrestamos();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tablePrestamos.getModel());
        tablePrestamos.setRowSorter(sorter);

        buscarTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }

            private void filtrar() {
                String texto = buscarTextField.getText();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });

        agregarButton.addActionListener(e -> {
            JComboBox<String> usuarioCombo = new JComboBox<>();
            JComboBox<String> libroCombo = new JComboBox<>();
            JComboBox<Integer> diasCombo = new JComboBox<>(new Integer[]{5, 10, 15});

            HashMap<String, Integer> mapaUsuarios = new HashMap<>();
            HashMap<String, Integer> mapaLibros = new HashMap<>();

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "")) {
                Statement stmt = conn.createStatement();

                ResultSet rsUsuarios = stmt.executeQuery("SELECT id, nombre FROM usuarios");
                while (rsUsuarios.next()) {
                    int id = rsUsuarios.getInt("id");
                    String nombre = rsUsuarios.getString("nombre");
                    String item = nombre + " (ID: " + id + ")";
                    usuarioCombo.addItem(item);
                    mapaUsuarios.put(item, id);
                }

                ResultSet rsLibros = stmt.executeQuery("SELECT id, titulo FROM libros WHERE disponible = 1");
                while (rsLibros.next()) {
                    int id = rsLibros.getInt("id");
                    String titulo = rsLibros.getString("titulo");
                    String item = titulo + " (ID: " + id + ")";
                    libroCombo.addItem(item);
                    mapaLibros.put(item, id);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar usuarios o libros: " + ex.getMessage());
                return;
            }

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Usuario:")); panel.add(usuarioCombo);
            panel.add(new JLabel("Libro:")); panel.add(libroCombo);
            panel.add(new JLabel("Días para devolución:")); panel.add(diasCombo);

            int result = JOptionPane.showConfirmDialog(null, panel, "Nuevo préstamo", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String usuarioSeleccionado = (String) usuarioCombo.getSelectedItem();
                    String libroSeleccionado = (String) libroCombo.getSelectedItem();

                    int userId = mapaUsuarios.get(usuarioSeleccionado);
                    int libroId = mapaLibros.get(libroSeleccionado);
                    int dias = (int) diasCombo.getSelectedItem();

                    LocalDate fechaPrestamo = LocalDate.now();
                    LocalDate fechaDevolucion = fechaPrestamo.plusDays(dias);

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    String sql = "INSERT INTO prestamos (user_id, libro_id, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, 0)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, userId);
                    ps.setInt(2, libroId);
                    ps.setDate(3, Date.valueOf(fechaPrestamo));
                    ps.setDate(4, Date.valueOf(fechaDevolucion));
                    ps.executeUpdate();

                    // Marcar libro como no disponible
                    PreparedStatement updateLibro = conn.prepareStatement("UPDATE libros SET disponible = 0 WHERE id = ?");
                    updateLibro.setInt(1, libroId);
                    updateLibro.executeUpdate();

                    conn.close();

                    JOptionPane.showMessageDialog(null, "Préstamo registrado correctamente.");
                    cargarTablaPrestamos();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar préstamo:\n" + ex.getMessage());
                }
            }
        });

        marcarComoDevueltoButton.addActionListener(e -> {
            int fila = tablePrestamos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un préstamo para marcar como devuelto.");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) tablePrestamos.getModel();
            int idPrestamo = (int) modelo.getValueAt(tablePrestamos.convertRowIndexToModel(fila), 0);
            String tituloLibro = modelo.getValueAt(tablePrestamos.convertRowIndexToModel(fila), 2).toString();

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "")) {

                // Obtener ID real del libro por su título
                PreparedStatement buscarLibro = conn.prepareStatement("SELECT id FROM libros WHERE titulo = ?");
                buscarLibro.setString(1, tituloLibro);
                ResultSet rs = buscarLibro.executeQuery();
                int libroId = -1;
                if (rs.next()) {
                    libroId = rs.getInt("id");
                }

                PreparedStatement ps = conn.prepareStatement("UPDATE prestamos SET devuelto = 1 WHERE id = ?");
                ps.setInt(1, idPrestamo);
                ps.executeUpdate();

                PreparedStatement updateLibro = conn.prepareStatement("UPDATE libros SET disponible = 1 WHERE id = ?");
                updateLibro.setInt(1, libroId);
                updateLibro.executeUpdate();

                JOptionPane.showMessageDialog(null, "Préstamo marcado como devuelto.");
                cargarTablaPrestamos();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar préstamo:\n" + ex.getMessage());
            }
        });
    }

    private void cargarTablaPrestamos() {
        String[] columnas = {"ID", "Usuario", "Libro", "Fecha Préstamo", "Fecha Devolución", "Devuelto"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
            SELECT p.id, u.nombre AS usuario, l.titulo AS libro,
                   p.fecha_prestamo, p.fecha_devolucion, p.devuelto
            FROM prestamos p
            JOIN usuarios u ON p.user_id = u.id
            JOIN libros l ON p.libro_id = l.id
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("usuario");
                fila[2] = rs.getString("libro");
                fila[3] = rs.getDate("fecha_prestamo").toString();
                fila[4] = rs.getDate("fecha_devolucion").toString();
                fila[5] = rs.getBoolean("devuelto") ? "Sí" : "No";
                modelo.addRow(fila);
            }

            tablePrestamos.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar préstamos:\n" + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
