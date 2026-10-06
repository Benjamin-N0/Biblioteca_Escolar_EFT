package vista;
import controlador.ControladorLogin;
import modelo.Usuario;

import javax.swing.*;

public class VentanaLogin extends JFrame{
    private JPanel panelPrincipal;
    private JPasswordField txtContrasenia;
    private JTextField txtRut;
    private JButton btnIngresar;
    private JLabel lblRut;
    private JLabel lblContrasenia;
    private JLabel lblTitulo;

    private ControladorLogin controladorLogin;

    public VentanaLogin() {

        controladorLogin = new ControladorLogin();

        setTitle("Biblioteca Escolar - Login");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        btnIngresar.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {

        String rut = txtRut.getText().trim();

        String contrasenia = new String(txtContrasenia.getPassword());

        if (rut.isEmpty() || contrasenia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar RUT y contrasenia."
            );
            return;
        }

        Usuario usuario =
                controladorLogin.iniciarSesion(
                        rut,
                        contrasenia
                );

        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido/a " + usuario.getNombre()
            );
            abrirVentanaPrincipal(usuario);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "RUT o contrasenia incorrectos."
            );
        }
    }

    private void abrirVentanaPrincipal(Usuario usuario) {

        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario);
        ventanaPrincipal.setVisible(true);
        dispose();
    }
}

