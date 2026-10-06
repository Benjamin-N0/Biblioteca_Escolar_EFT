package vista;

import modelo.Usuario;
import javax.swing.*;

public class VentanaPrincipal extends JFrame{
    private JPanel panelPrincipal;
    private JButton btnCerrarSesion;
    private JButton btnReportes;
    private JButton btnPrestamos;
    private JButton btnEstudiantes;
    private JButton btnLibros;
    private JLabel lblBienvenida;
    private JLabel lblRol;

    private Usuario usuario;

    public VentanaPrincipal(Usuario usuario) {

        this.usuario = usuario;
        setTitle("Biblioteca Escolar");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        mostrarDatosUsuario();
        configurarPermisos();

        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        btnLibros.addActionListener(e -> {VentanaLibros ventana = new VentanaLibros();
            ventana.setVisible(true);
        });

        btnEstudiantes.addActionListener(e -> {VentanaEstudiantes ventana = new VentanaEstudiantes();
            ventana.setVisible(true);
        });

        btnPrestamos.addActionListener(e -> {VentanaPrestamos ventana = new VentanaPrestamos(usuario);
            ventana.setVisible(true);
        });

        btnReportes.addActionListener(e -> {VentanaReportes ventana = new VentanaReportes(usuario);
            ventana.setVisible(true);
        });

    }

    private void mostrarDatosUsuario() {

        lblBienvenida.setText("Bienvenido/a: " + usuario.getNombre()
        );

        lblRol.setText("Rol: " + usuario.getRol()
        );
    }

    private void configurarPermisos() {

        if (usuario.getRol().equals("bibliotecario")) {


            btnLibros.setEnabled(true);
            btnEstudiantes.setEnabled(true);
            btnPrestamos.setEnabled(true);
            btnReportes.setEnabled(true);

        } else if (usuario.getRol().equals("estudiante")) {

            btnLibros.setEnabled(false);
            btnEstudiantes.setEnabled(false);
            btnPrestamos.setEnabled(true);
            btnReportes.setEnabled(true);
        }
    }

    private void cerrarSesion() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar la sesion?",
                "Cerrar sesion",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.setVisible(true);
            dispose();
        }
    }
}