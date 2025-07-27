package Interfaces.Compartido;

import javax.swing.*;

public class Sidebar {
    private JPanel panel1;
    private JButton inicioButton;
    private JButton gestiónDeLibrosButton;
    private JButton gestiónDeUsuariosButton;
    private JButton gestionDePréstamosButton;
    private JButton cerrarSesiónButton;

    public JPanel getPanelSidebar() {return panel1;}
    public JButton getGestionDeLibrosButton() {
        return gestiónDeLibrosButton;
    }
    public JButton getInicioButton() {
        return inicioButton;
    }
    public JButton getcerrarSesiónButton(){
        return cerrarSesiónButton;
    }
    public JButton getGestionDeUsuariosButton() {return gestiónDeUsuariosButton;}
    public JButton getGestionDePréstamosButton(){return gestionDePréstamosButton;}
}
