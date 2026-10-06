package dao.impl;

import dao.LibroDAO;
import modelo.Libro;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private Connection conexion;

    public LibroDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // GUARDAR LIBRO

    @Override
    public void guardar(Libro libro) {

        String sql =
                "INSERT INTO libros " +
                        "(titulo, autor, isbn, editorial, stock, id_categoria) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setString(4, libro.getEditorial());
            statement.setInt(5, libro.getStock());
            statement.setInt(6, libro.getIdCategoria());

            statement.executeUpdate();

            ResultSet resultado = statement.getGeneratedKeys();

            if (resultado.next()) {

                libro.setId(
                        resultado.getInt(1)
                );
            }

            System.out.println(
                    "Libro guardado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar libro."
            );
            e.printStackTrace();
        }
    }

    // BUSCAR LIBRO POR ID

    @Override
    public Libro buscarPorId(int id) {

        String sql = "SELECT * FROM libros WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultado =
                    statement.executeQuery();

            if (resultado.next()) {

                return convertirLibro(resultado);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar libro."
            );
            e.printStackTrace();
        }

        return null;
    }

    // LISTAR LIBROS

    @Override
    public List<Libro> listar() {

        List<Libro> libros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM libros";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                libros.add(
                        convertirLibro(resultado)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar libros."
            );

            e.printStackTrace();
        }

        return libros;
    }

    // ACTUALIZAR LIBRO

    @Override
    public void actualizar(Libro libro) {

        String sql =
                "UPDATE libros SET " +
                        "titulo = ?, " +
                        "autor = ?, " +
                        "isbn = ?, " +
                        "editorial = ?, " +
                        "stock = ?, " +
                        "id_categoria = ? " +
                        "WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setString(4, libro.getEditorial());
            statement.setInt(5, libro.getStock());
            statement.setInt(6, libro.getIdCategoria());
            statement.setInt(7, libro.getId());

            statement.executeUpdate();

            System.out.println(
                    "Libro actualizado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar libro."
            );

            e.printStackTrace();
        }
    }

    // ELIMINAR LIBRO

    @Override
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM libros WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas =
                    statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Libro eliminado correctamente."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar libro."
            );

            e.printStackTrace();
        }

        return false;
    }

    // CONVERTIR RESULTSET EN OBJETO LIBRO

    private Libro convertirLibro(
            ResultSet resultado) throws SQLException {

        Libro libro = new Libro();

        libro.setId(
                resultado.getInt("id")
        );

        libro.setTitulo(
                resultado.getString("titulo")
        );

        libro.setAutor(
                resultado.getString("autor")
        );

        libro.setIsbn(
                resultado.getString("isbn")
        );

        libro.setEditorial(
                resultado.getString("editorial")
        );

        libro.setStock(
                resultado.getInt("stock")
        );

        libro.setIdCategoria(
                resultado.getInt("id_categoria")
        );

        return libro;
    }
}