package Interfaces.Login;

import Modelos.Empleado;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Registro {
    private JPanel panelRegistro;
    private JPanel panelInicio;
    private JTextField userTextField;
    private JPasswordField passwordPasswordField;
    private JButton iniciarButton;
    private JButton registrarmeButton;
    private JPasswordField confirmationPasswordField;
    private JTextField locationTextField;
    private JTextField phoneTextField;
    private JTextField DNITextField;
    private JTextField correoElectrónicoTextField;

    private JFrame frame;  // referencia al JFrame principal

    public Registro(JFrame frame) {
        this.frame = frame;

        userTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        passwordPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        phoneTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        locationTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        confirmationPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        DNITextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        correoElectrónicoTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));

        iniciarButton.addActionListener(e -> {
            Inicio inicio = new Inicio(frame);
            Dimension inicioSize = inicio.getPanelLogin().getSize();
            frame.setContentPane(inicio.getPanelLogin());
            frame.setSize(inicioSize);
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });
        registrarmeButton.addActionListener(e -> {
            String username = userTextField.getText().trim();
            String password = String.valueOf(passwordPasswordField.getPassword()).trim();
            String confirmPassword = String.valueOf(confirmationPasswordField.getPassword()).trim();
            String correo = correoElectrónicoTextField.getText().trim();
            String dniStr = DNITextField.getText().trim();
            String numeroStr = phoneTextField.getText().trim();
            String direccion = locationTextField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                    correo.isEmpty() || dniStr.isEmpty() || numeroStr.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.");
                return;
            }

            if (!correo.contains("@") || !correo.contains(".")) {
                JOptionPane.showMessageDialog(null, "Correo electrónico inválido.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                return;
            }

            if (!dniStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "El DNI debe contener solo números.");
                return;
            }

            if (!numeroStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "El número telefónico debe contener solo números.");
                return;
            }

            long dni = Long.parseLong(dniStr);
            long numero = Long.parseLong(numeroStr);

            Empleado empleado = new Empleado(0, dni, correo, numero, direccion, username, password);

            if (registrarEmpleado(empleado)) {
                JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                limpiarCampos();
                Inicio inicio = new Inicio(frame);
                frame.setContentPane(inicio.getPanelLogin());
                frame.setSize(500, 600);
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar. Inténtalo nuevamente.");
            }

        });
        Color placeholderColor = Color.GRAY;
        Color inputColor = Color.BLACK;

        userTextField.setText("Usuario");
        userTextField.setForeground(placeholderColor);

        passwordPasswordField.setText("Contraseña");
        passwordPasswordField.setForeground(placeholderColor);

        confirmationPasswordField.setText("Confirmar contraseña");
        confirmationPasswordField.setForeground(placeholderColor);

        correoElectrónicoTextField.setText("Correo electrónico");
        correoElectrónicoTextField.setForeground(placeholderColor);

        DNITextField.setText("DNI");
        DNITextField.setForeground(placeholderColor);

        phoneTextField.setText("Número");
        phoneTextField.setForeground(placeholderColor);

        locationTextField.setText("Dirección");
        locationTextField.setForeground(placeholderColor);

        addPlaceholderBehavior(userTextField, "Usuario");
        addPlaceholderBehavior(passwordPasswordField, "Contraseña");
        addPlaceholderBehavior(confirmationPasswordField, "Confirmar contraseña");
        addPlaceholderBehavior(correoElectrónicoTextField, "Correo electrónico");
        addPlaceholderBehavior(DNITextField, "DNI");
        addPlaceholderBehavior(phoneTextField, "Número");
        addPlaceholderBehavior(locationTextField, "Dirección");

    }
    private void addPlaceholderBehavior(JTextField textField, String placeholder) {
        Color placeholderColor = Color.GRAY;
        Color inputColor = Color.BLACK;

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(inputColor);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }

    private void addPlaceholderBehavior(JPasswordField passwordField, String placeholder) {
        Color placeholderColor = Color.GRAY;
        Color inputColor = Color.BLACK;

        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(inputColor);
                    passwordField.setEchoChar('•');
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(placeholderColor);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }
    private void limpiarCampos() {
        userTextField.setText("Usuario");
        passwordPasswordField.setText("Contraseña");
        confirmationPasswordField.setText("Confirmar contraseña");
        correoElectrónicoTextField.setText("Correo electrónico");
        DNITextField.setText("DNI");
        phoneTextField.setText("Número");
        locationTextField.setText("Dirección");

        Color placeholderColor = Color.GRAY;
        userTextField.setForeground(placeholderColor);
        passwordPasswordField.setForeground(placeholderColor);
        passwordPasswordField.setEchoChar((char) 0);
        confirmationPasswordField.setForeground(placeholderColor);
        confirmationPasswordField.setEchoChar((char) 0);
        correoElectrónicoTextField.setForeground(placeholderColor);
        DNITextField.setForeground(placeholderColor);
        phoneTextField.setForeground(placeholderColor);
        locationTextField.setForeground(placeholderColor);
    }

    private boolean registrarEmpleado(Empleado empleado) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "")) {
            String sql = "INSERT INTO empleados (username, password, dni, correo, numero, direccion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, empleado.getUsername());
            stmt.setString(2, empleado.getPassword());
            stmt.setLong(3, empleado.getDni());
            stmt.setString(4, empleado.getCorreo());
            stmt.setLong(5, empleado.getNumero());
            stmt.setString(6, empleado.getDireccion());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public JPanel getPanelRegistro() {
        return panelRegistro;
    }
}
