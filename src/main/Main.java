package main;
import Interfaces.Login.Inicio;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        Inicio inicio = new Inicio(frame);
        frame.setContentPane(inicio.getPanelLogin());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

