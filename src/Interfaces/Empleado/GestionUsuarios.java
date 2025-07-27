package Interfaces.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

public class GestionUsuarios {
    private JPanel panel1;
    private JTextField buscarTextField;
    private JTable tableUsuarios;
    private JButton agregarButton;
    private JButton editarButton;

    public GestionUsuarios() {
        cargarTablaUsuarios();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableUsuarios.getModel());
        tableUsuarios.setRowSorter(sorter);

        buscarTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }

            private void filtrar() {
                String texto = buscarTextField.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });

        agregarButton.addActionListener(e -> {
            JTextField nombreField = new JTextField();
            JTextField correoField = new JTextField();
            JTextField dniField = new JTextField();
            JTextField numeroField = new JTextField();
            JTextField direccionField = new JTextField();

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Nombre:"));
            panel.add(nombreField);
            panel.add(new JLabel("Correo electrónico:"));
            panel.add(correoField);
            panel.add(new JLabel("DNI:"));
            panel.add(dniField);
            panel.add(new JLabel("Número telefónico:"));
            panel.add(numeroField);
            panel.add(new JLabel("Dirección:"));
            panel.add(direccionField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nuevo usuario", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nombre = nombreField.getText().trim();
                    String correo = correoField.getText().trim();
                    String dniStr = dniField.getText().trim();
                    String numeroStr = numeroField.getText().trim();
                    String direccion = direccionField.getText().trim();

                    if (nombre.isEmpty()  || correo.isEmpty() ||
                            dniStr.isEmpty() || numeroStr.isEmpty() || direccion.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        return;
                    }
                    if (!dniStr.matches("\\d+") || !numeroStr.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "DNI y número deben ser solo números.");
                        return;
                    }

                    long dni = Long.parseLong(dniStr);
                    long numero = Long.parseLong(numeroStr);

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    String sql = "INSERT INTO usuarios (nombre, dni, correo, numero, direccion) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setLong(2, dni);
                    ps.setString(3, correo);
                    ps.setLong(4, numero);
                    ps.setString(5, direccion);

                    int rows = ps.executeUpdate();
                    conn.close();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.");
                        cargarTablaUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar usuario:\n" + ex.getMessage());
                }
            }
        });

        editarButton.addActionListener(e -> {
            int filaSeleccionada = tableUsuarios.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un usuario para editar.");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) tableUsuarios.getModel();
            int modelRow = tableUsuarios.convertRowIndexToModel(filaSeleccionada);

            int id = (int) modelo.getValueAt(modelRow, 0);
            String nombreActual = (String) modelo.getValueAt(modelRow, 1);
            long dniActual = Long.parseLong(modelo.getValueAt(modelRow, 2).toString());
            String correoActual = (String) modelo.getValueAt(modelRow, 3);
            long numeroActual = Long.parseLong(modelo.getValueAt(modelRow, 4).toString());
            String direccionActual = (String) modelo.getValueAt(modelRow, 5);

            JTextField nombreField = new JTextField(nombreActual);
            JTextField correoField = new JTextField(correoActual);
            JTextField dniField = new JTextField(String.valueOf(dniActual));
            JTextField numeroField = new JTextField(String.valueOf(numeroActual));
            JTextField direccionField = new JTextField(direccionActual);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Nombre:"));
            panel.add(nombreField);
            panel.add(new JLabel("Correo electrónico:"));
            panel.add(correoField);
            panel.add(new JLabel("DNI:"));
            panel.add(dniField);
            panel.add(new JLabel("Número telefónico:"));
            panel.add(numeroField);
            panel.add(new JLabel("Dirección:"));
            panel.add(direccionField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editar usuario", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nombre = nombreField.getText().trim();
                    String correo = correoField.getText().trim();
                    String dniStr = dniField.getText().trim();
                    String numeroStr = numeroField.getText().trim();
                    String direccion = direccionField.getText().trim();

                    if (nombre.isEmpty()  || correo.isEmpty() ||
                            dniStr.isEmpty() || numeroStr.isEmpty() || direccion.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        return;
                    }
                    if (!dniStr.matches("\\d+") || !numeroStr.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "DNI y número deben ser solo números.");
                        return;
                    }

                    long dni = Long.parseLong(dniStr);
                    long numero = Long.parseLong(numeroStr);

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    String sql = "UPDATE usuarios SET nombre = ?, dni = ?, correo = ?, numero = ?, direccion = ? WHERE id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setLong(2, dni);
                    ps.setString(3, correo);
                    ps.setLong(4, numero);
                    ps.setString(5, direccion);
                    ps.setInt(6, id);

                    int rows = ps.executeUpdate();
                    conn.close();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
                        cargarTablaUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario:\n" + ex.getMessage());
                }
            }
        });
    }

    private void cargarTablaUsuarios() {
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String usuario = "root";
        String contrasena = "";

        String[] columnas = {"ID", "Nombre", "DNI", "Correo", "Número", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // no editable en la tabla
            }
        };

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getLong("dni");
                fila[3] = rs.getString("correo");
                fila[4] = rs.getLong("numero");
                fila[5] = rs.getString("direccion");
                modelo.addRow(fila);
            }

            tableUsuarios.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios:\n" + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
