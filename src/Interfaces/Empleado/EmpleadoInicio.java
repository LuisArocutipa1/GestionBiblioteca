package Interfaces.Empleado;

import javax.swing.*;
import java.sql.*;

public class EmpleadoInicio {
    private JPanel panel1;
    private JPanel cantidadLibros;
    private JPanel cantidadUsuarios;
    private JPanel PrestamosActivos;
    private JPanel PrestamosVencidos;
    private JLabel labelprestamosActivos;
    private JLabel labelCantidadLibros;
    private JLabel labelCantidadUsuarios;
    private JLabel labelPrestamosVencidos;
    private JLabel bienvenido;

    public EmpleadoInicio() {
        cargarResumen();
    }

    private void cargarResumen() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "")) {

            // Total de libros
            Statement stmt1 = conn.createStatement();
            ResultSet rsLibros = stmt1.executeQuery("SELECT COUNT(*) FROM libros");
            if (rsLibros.next()) {
                labelCantidadLibros.setText(String.valueOf(rsLibros.getInt(1)));
            }

            // Total de usuarios
            Statement stmt2 = conn.createStatement();
            ResultSet rsUsuarios = stmt2.executeQuery("SELECT COUNT(*) FROM usuarios");
            if (rsUsuarios.next()) {
                labelCantidadUsuarios.setText(String.valueOf(rsUsuarios.getInt(1)));
            }

            // Préstamos activos (no devueltos)
            Statement stmt3 = conn.createStatement();
            ResultSet rsActivos = stmt3.executeQuery("SELECT COUNT(*) FROM prestamos WHERE devuelto = 0");
            if (rsActivos.next()) {
                labelprestamosActivos.setText(String.valueOf(rsActivos.getInt(1)));
            }

            // Préstamos vencidos (fecha_devolucion < hoy y no devuelto)
            PreparedStatement psVencidos = conn.prepareStatement("SELECT COUNT(*) FROM prestamos WHERE devuelto = 0 AND fecha_devolucion < CURDATE()");
            ResultSet rsVencidos = psVencidos.executeQuery();
            if (rsVencidos.next()) {
                labelPrestamosVencidos.setText(String.valueOf(rsVencidos.getInt(1)));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar resumen: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
