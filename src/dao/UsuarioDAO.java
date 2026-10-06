package dao;

import modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    void guardar(Usuario usuario);

    Usuario buscarPorId(int id);

    Usuario buscarPorRutYContrasena(String rut, String contraseña);

    List<Usuario> listar();

    void actualizar(Usuario usuario);

    boolean eliminar(int id);
}