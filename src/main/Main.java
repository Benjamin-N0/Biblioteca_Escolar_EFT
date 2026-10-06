package main;

import vista.VentanaLogin;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaLogin ventanaLogin = new VentanaLogin();

            ventanaLogin.setVisible(true);
        });
    }
}