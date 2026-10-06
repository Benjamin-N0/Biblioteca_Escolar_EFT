package dao.impl;

import dao.CategoriaDAO;
import modelo.Categoria;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    private Connection conexion;

    public CategoriaDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // GUARDAR CATEGORIA

    @Override
    public void guardar(Categoria categoria) {

        String sql =
                "INSERT INTO categorias (nombre) VALUES (?)";

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(
                    1,
                    categoria.getNombre()
            );

            statement.executeUpdate();

            ResultSet resultado =
                    statement.getGeneratedKeys();

            if (resultado.next()) {

                categoria.setId(
                        resultado.getInt(1)
                );
            }

            System.out.println(
                    "Categoria guardada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar categoria."
            );

            e.printStackTrace();
        }
    }

    // BUSCAR CATEGORIA POR ID

    @Override
    public Categoria buscarPorId(int id) {

        String sql = "SELECT * FROM categorias WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                return convertirCategoria(resultado);
            }
        } catch (SQLException e) {

            System.out.println("Error al buscar categoria.");

            e.printStackTrace();
        }
        return null;
    }

    // LISTAR CATEGORIAS

    @Override
    public List<Categoria> listar() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        try (PreparedStatement statement = conexion.prepareStatement(sql);

             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                categorias.add(
                        convertirCategoria(resultado)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar categorias."
            );

            e.printStackTrace();
        }
        return categorias;
    }

    // ACTUALIZAR CATEGORIA

    @Override
    public void actualizar(Categoria categoria) {

        String sql = "UPDATE categorias SET nombre = ? " + "WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    categoria.getNombre()
            );

            statement.setInt(
                    2,
                    categoria.getId()
            );

            statement.executeUpdate();

            System.out.println(
                    "Categoria actualizada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar categoria."
            );

            e.printStackTrace();
        }
    }

    // ELIMINAR CATEGORIA

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM categorias WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas =
                    statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Categoria eliminada correctamente."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar categoria."
            );
            e.printStackTrace();
        }

        return false;
    }

    // CONVERTIR RESULTSET EN OBJETO CATEGORIA

    private Categoria convertirCategoria(
            ResultSet resultado) throws SQLException {

        Categoria categoria = new Categoria();

        categoria.setId(
                resultado.getInt("id")
        );

        categoria.setNombre(
                resultado.getString("nombre")
        );

        return categoria;
    }
}