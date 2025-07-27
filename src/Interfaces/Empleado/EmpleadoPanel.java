package Interfaces.Empleado;

import Interfaces.Compartido.Sidebar;
import Interfaces.Login.Inicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import com.intellij.uiDesigner.core.GridConstraints;

public class EmpleadoPanel {
    private JPanel panel1;
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    private JFrame frame;
    public EmpleadoPanel(JFrame frame) {
            this.frame = frame;
            Sidebar sb = new Sidebar();

            // Cargar Sidebar
            JPanel sideBarContent = sb.getPanelSidebar();
            sidebarPanel.removeAll();
            sidebarPanel.setLayout(new BorderLayout()); // Asegura el layout
            sidebarPanel.add(sideBarContent, BorderLayout.CENTER);
            sidebarPanel.revalidate();
            sidebarPanel.repaint();

            // Cargar la vista inicial
            cargarEmpleadoInicio();

            // Eventos de navegación
            sb.getGestionDeLibrosButton().addActionListener(e -> cargarGestionLibros());
            sb.getInicioButton().addActionListener(e -> cargarEmpleadoInicio());
            sb.getGestionDeUsuariosButton().addActionListener(e -> cargarGestionDeUsuarios());
            sb.getGestionDePréstamosButton().addActionListener(e -> cargarGestionDePrestamos());
            sb.getcerrarSesiónButton().addActionListener(e -> {
                Inicio inicio = new Inicio(frame);
                frame.setContentPane(inicio.getPanelLogin());
                frame.setSize(500, 600);
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            });
        }


    private void cargarGestionLibros() {
    GestionLibros gestionLibros = new GestionLibros();
    contentPanel.removeAll();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.add(gestionLibros.getPanel(), BorderLayout.CENTER);
    contentPanel.revalidate();
    contentPanel.repaint();
    }
    private void cargarEmpleadoInicio(){
        EmpleadoInicio empleadoInicio = new EmpleadoInicio();
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(empleadoInicio.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void cargarGestionDeUsuarios() {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(gestionUsuarios.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void cargarGestionDePrestamos() {
        Prestamos prestamos = new Prestamos();
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(prestamos.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void cargarCerrarSesión(){
        Inicio inicio = new Inicio(frame);
        panel1.removeAll();
        panel1.setLayout(new BorderLayout());
        panel1.add(inicio.getPanelLogin(), BorderLayout.CENTER);
        panel1.revalidate();
        panel1.repaint();
    }
    public JPanel getPanelempleadoPanel() {return panel1;}


}
