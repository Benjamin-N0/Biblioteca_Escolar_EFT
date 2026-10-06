package controlador;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import modelo.Usuario;

public class ControladorLogin {

    private UsuarioDAO usuarioDAO;

    public ControladorLogin() {

        usuarioDAO = new UsuarioDAOImpl();
    }

    public Usuario iniciarSesion(String rut, String contrasenia) {

        return usuarioDAO.buscarPorRutYContrasena(
                rut,
                contrasenia
        );
    }
}