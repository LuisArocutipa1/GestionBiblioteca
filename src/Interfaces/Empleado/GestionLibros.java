package Interfaces.Empleado;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class GestionLibros {
    private JPanel panel1;
    private JTextField buscarTextField;
    private JTable tableLibros;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;

    public GestionLibros() {
        cargarTablaLibros();
        agregarButton.addActionListener(e -> {
            JTextField tituloField = new JTextField();
            JTextField autorField = new JTextField();
            JTextField generoField = new JTextField();
            JTextField anioField = new JTextField();
            JTextField isbnField = new JTextField();
            JCheckBox disponibleCheck = new JCheckBox("Disponible");

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Título:")); panel.add(tituloField);
            panel.add(new JLabel("Autor:")); panel.add(autorField);
            panel.add(new JLabel("Género:")); panel.add(generoField);
            panel.add(new JLabel("Año de publicación:")); panel.add(anioField);
            panel.add(new JLabel("ISBN:")); panel.add(isbnField);
            panel.add(disponibleCheck);

            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nuevo libro", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String titulo = tituloField.getText().trim();
                    String autor = autorField.getText().trim();
                    String genero = generoField.getText().trim();
                    int anio = Integer.parseInt(anioField.getText().trim());
                    String isbn = isbnField.getText().trim();
                    boolean disponible = disponibleCheck.isSelected();

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO libros (titulo, autor, genero, anio_publicacion, isbn, disponible) VALUES (?, ?, ?, ?, ?, ?)");
                    ps.setString(1, titulo);
                    ps.setString(2, autor);
                    ps.setString(3, genero);
                    ps.setInt(4, anio);
                    ps.setString(5, isbn);
                    ps.setBoolean(6, disponible);

                    ps.executeUpdate();
                    conn.close();

                    JOptionPane.showMessageDialog(null, "Libro agregado exitosamente.");
                    cargarTablaLibros(); // actualiza la tabla

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar el libro:\n" + ex.getMessage());
                }
            }
        });
        editarButton.addActionListener(e -> {
            int filaSeleccionada = tableLibros.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un libro para editar.");
                return;
            }

            // Obtener datos actuales de la fila seleccionada
            DefaultTableModel modelo = (DefaultTableModel) tableLibros.getModel();
            int id = (int) modelo.getValueAt(filaSeleccionada, 0);
            String tituloActual = (String) modelo.getValueAt(filaSeleccionada, 1);
            String autorActual = (String) modelo.getValueAt(filaSeleccionada, 2);
            String generoActual = (String) modelo.getValueAt(filaSeleccionada, 3);
            int anioActual = (int) modelo.getValueAt(filaSeleccionada, 4);
            String isbnActual = (String) modelo.getValueAt(filaSeleccionada, 5);
            boolean disponibleActual = "Sí".equals(modelo.getValueAt(filaSeleccionada, 6));

            // Crear campos con los datos actuales
            JTextField tituloField = new JTextField(tituloActual);
            JTextField autorField = new JTextField(autorActual);
            JTextField generoField = new JTextField(generoActual);
            JTextField anioField = new JTextField(String.valueOf(anioActual));
            JTextField isbnField = new JTextField(isbnActual);
            JCheckBox disponibleCheck = new JCheckBox("Disponible", disponibleActual);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Título:")); panel.add(tituloField);
            panel.add(new JLabel("Autor:")); panel.add(autorField);
            panel.add(new JLabel("Género:")); panel.add(generoField);
            panel.add(new JLabel("Año de publicación:")); panel.add(anioField);
            panel.add(new JLabel("ISBN:")); panel.add(isbnField);
            panel.add(disponibleCheck);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editar libro", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String titulo = tituloField.getText().trim();
                    String autor = autorField.getText().trim();
                    String genero = generoField.getText().trim();
                    int anio = Integer.parseInt(anioField.getText().trim());
                    String isbn = isbnField.getText().trim();
                    boolean disponible = disponibleCheck.isSelected();

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    PreparedStatement ps = conn.prepareStatement(
                            "UPDATE libros SET titulo = ?, autor = ?, genero = ?, anio_publicacion = ?, isbn = ?, disponible = ? WHERE id = ?"
                    );
                    ps.setString(1, titulo);
                    ps.setString(2, autor);
                    ps.setString(3, genero);
                    ps.setInt(4, anio);
                    ps.setString(5, isbn);
                    ps.setBoolean(6, disponible);
                    ps.setInt(7, id);

                    ps.executeUpdate();
                    conn.close();

                    JOptionPane.showMessageDialog(null, "Libro actualizado exitosamente.");
                    cargarTablaLibros(); // refresca la tabla

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el libro:\n" + ex.getMessage());
                }
            }
        });
        eliminarButton.addActionListener(e -> {
            int filaSeleccionada = tableLibros.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un libro para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el libro seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // el usuario canceló
            }

            DefaultTableModel modelo = (DefaultTableModel) tableLibros.getModel();
            int id = (int) modelo.getValueAt(filaSeleccionada, 0);

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                PreparedStatement ps = conn.prepareStatement("DELETE FROM libros WHERE id = ?");
                ps.setInt(1, id);

                int filasAfectadas = ps.executeUpdate();
                conn.close();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Libro eliminado correctamente.");
                    cargarTablaLibros();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el libro.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el libro:\n" + ex.getMessage());
            }
        });

    }

    private void cargarTablaLibros() {
        String url = "jdbc:mysql://localhost:3306/biblioteca"; // base de datos
        String usuario = "root";
        String contrasena = ""; // por defecto en XAMPP

        String[] columnas = {"ID", "Título", "Autor", "Género", "Año", "ISBN", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM libros")) {

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("titulo");
                fila[2] = rs.getString("autor");
                fila[3] = rs.getString("genero");
                fila[4] = rs.getInt("anio_publicacion");
                fila[5] = rs.getString("isbn");
                fila[6] = rs.getBoolean("disponible") ? "Sí" : "No";
                modelo.addRow(fila);
            }

            assert tableLibros != null;
            tableLibros.setModel(modelo);
            tableLibros.getTableHeader().setReorderingAllowed(false);

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
            tableLibros.setRowSorter(sorter);
            sorter.setComparator(0, (o1, o2) -> {
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                return i1.compareTo(i2);
            });


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los libros:\n" + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel1;
    }

}
