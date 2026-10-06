package dao.impl;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection conexion;

    public UsuarioDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // GUARDAR USUARIO

    @Override
    public void guardar(Usuario usuario) {

        String sql = "INSERT INTO usuarios " + "(nombre, rut, correo, contraseña, rol) " + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getRut());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getContrasenia());
            statement.setString(5, usuario.getRol());

            statement.executeUpdate();

            ResultSet resultado = statement.getGeneratedKeys();

            if (resultado.next()) {
                usuario.setId(resultado.getInt(1));
            }

            System.out.println(
                    "Usuario guardado correctamente."
            );
        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar usuario."
            );
            e.printStackTrace();
        }
    }

    // BUSCAR USUARIO POR ID

    @Override
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                return convertirUsuario(resultado);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar usuario."
            );

            e.printStackTrace();
        }

        return null;
    }

    // BUSCAR USUARIO PARA LOGIN

    @Override
    public Usuario buscarPorRutYContrasena(
            String rut,
            String contrasenia) {

        String sql = "SELECT * FROM usuarios " + "WHERE rut = ? AND contraseña = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, rut);
            statement.setString(2, contrasenia);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                return convertirUsuario(resultado);
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar usuario para iniciar sesion.");

            e.printStackTrace();
        }

        return null;
    }

    // LISTAR USUARIOS

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                usuarios.add(
                        convertirUsuario(resultado)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar usuarios."
            );

            e.printStackTrace();
        }

        return usuarios;
    }

    // ACTUALIZAR USUARIO

    @Override
    public void actualizar(Usuario usuario) {

        String sql =
                "UPDATE usuarios SET " +
                        "nombre = ?, " +
                        "rut = ?, " +
                        "correo = ?, " +
                        "contraseña = ?, " +
                        "rol = ? " +
                        "WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getRut());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getContrasenia());
            statement.setString(5, usuario.getRol());
            statement.setInt(6, usuario.getId());

            statement.executeUpdate();

            System.out.println(
                    "Usuario actualizado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario."
            );

            e.printStackTrace();
        }
    }

    // ELIMINAR USUARIO

    @Override
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Usuario eliminado correctamente."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario."
            );

            e.printStackTrace();
        }

        return false;
    }

    // CONVERTIR RESULTSET EN OBJETO USUARIO

    private Usuario convertirUsuario(

            ResultSet resultado) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(resultado.getInt("id"));

        usuario.setNombre(resultado.getString("nombre"));

        usuario.setRut(resultado.getString("rut"));

        usuario.setCorreo(resultado.getString("correo"));

        usuario.setContrasenia(resultado.getString("contraseña"));

        usuario.setRol(resultado.getString("rol"));

        return usuario;
    }
}