package Interfaces.Login;

import Interfaces.Empleado.EmpleadoPanel;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import Modelos.Empleado;

public class Inicio {
    private JPanel panelLogin;
    private JTextField userTextField;
    private JPasswordField passwordPasswordField;
    private JButton iniciarButton;
    private JButton registrarmeButton;
    private JPanel panelInicio;
    private JPanel panelFondo;

    private JFrame frame;

    public Inicio(JFrame frame) {
        panelLogin.setSize(600, 330);
        this.frame = frame;
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear instancia del panel registro
                Registro registro = new Registro(frame);
                // Cambiar contenido del JFrame
                frame.setContentPane(registro.getPanelRegistro());
                frame.setSize(500, 600);
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            }
        });
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userTextField.getText();
                String password = String.valueOf(passwordPasswordField.getPassword());
                Empleado empleado = validarUsuario(user, password);
                if (empleado != null) {
                    EmpleadoPanel empleadoPanel = new EmpleadoPanel(frame);
                    frame.setContentPane(empleadoPanel.getPanelempleadoPanel());
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, " Credenciales incorrectas ");
                }

            }
        });


        userTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        passwordPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));

        userTextField.setText("Usuario");
        userTextField.setForeground(Color.GRAY);
        passwordPasswordField.setText("Contraseña");
        passwordPasswordField.setForeground(Color.GRAY);

        userTextField.setFocusable(false);
        passwordPasswordField.setFocusable(false);
        panelLogin.setFocusable(true);

        configurarFondo();

        userTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                userTextField.setFocusable(true);
                userTextField.requestFocusInWindow();

                if (userTextField.getText().equals("Usuario")) {
                    userTextField.setText("");
                    userTextField.setForeground(Color.BLACK);
                }
                if (new String(passwordPasswordField.getPassword()).isEmpty()) {
                    passwordPasswordField.setText("Contraseña");
                    passwordPasswordField.setForeground(Color.GRAY);
                    passwordPasswordField.setFocusable(false);
                }
            }
        });

        passwordPasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                passwordPasswordField.setFocusable(true);
                passwordPasswordField.requestFocusInWindow();

                if (new String(passwordPasswordField.getPassword()).equals("Contraseña")) {
                    passwordPasswordField.setText("");
                    passwordPasswordField.setForeground(Color.BLACK);
                }
                if (userTextField.getText().isEmpty()) {
                    userTextField.setText("Usuario");
                    userTextField.setForeground(Color.GRAY);
                    userTextField.setFocusable(false);
                }
            }
        });

        SwingUtilities.invokeLater(() -> panelLogin.requestFocusInWindow());
    }

    private Empleado validarUsuario(String username, String password) {
        Empleado empleado = null;
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/biblioteca", "root", ""
            );
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM empleados WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getLong("dni"),
                        rs.getString("correo"),
                        rs.getLong("numero"),
                        rs.getString("direccion"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empleado;
    }



    public Inicio() {

    }

    private void configurarFondo() {
        MouseAdapter quitarFocoListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                quitarFocoCampos();

                if (userTextField.getText().isEmpty()) {
                    userTextField.setText("Usuario");
                    userTextField.setForeground(Color.GRAY);
                    userTextField.setFocusable(false);
                }
                if (new String(passwordPasswordField.getPassword()).isEmpty()) {
                    passwordPasswordField.setText("Contraseña");
                    passwordPasswordField.setForeground(Color.GRAY);
                    passwordPasswordField.setFocusable(false);
                }
            }
        };

        panelLogin.addMouseListener(quitarFocoListener);
        if (panelInicio != null) panelInicio.addMouseListener(quitarFocoListener);
        if (panelFondo != null) panelFondo.addMouseListener(quitarFocoListener);
    }

    private void quitarFocoCampos() {
        userTextField.setFocusable(false);
        passwordPasswordField.setFocusable(false);
        panelLogin.requestFocusInWindow();
    }

    public JPanel getPanelLogin() {
        return panelLogin;
    }
}
