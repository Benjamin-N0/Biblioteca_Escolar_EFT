package dao.impl;

import dao.EstudianteDAO;
import modelo.Estudiante;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    private Connection conexion;

    public EstudianteDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }


    // GUARDAR ESTUDIANTE


    @Override
    public void guardar(Estudiante estudiante) {

        String sql = "INSERT INTO estudiantes " +
                "(nombre, rut, curso, correo) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getRut());
            statement.setString(3, estudiante.getCurso());
            statement.setString(4, estudiante.getCorreo());

            statement.executeUpdate();

            // Obtener el ID generado por MySQL
            ResultSet resultado = statement.getGeneratedKeys();

            if (resultado.next()) {
                estudiante.setId(resultado.getInt(1));
            }

            System.out.println(
                    "Estudiante guardado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar estudiante."
            );

            e.printStackTrace();
        }
    }

    // BUSCAR ESTUDIANTE POR ID

    @Override
    public Estudiante buscarPorId(int id) {

        String sql =
                "SELECT * FROM estudiantes WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                return convertirEstudiante(resultado);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar estudiante."
            );

            e.printStackTrace();
        }

        return null;
    }


    // LISTAR ESTUDIANTES


    @Override
    public List<Estudiante> listar() {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT * FROM estudiantes";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                estudiantes.add(
                        convertirEstudiante(resultado)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar estudiantes."
            );

            e.printStackTrace();
        }

        return estudiantes;
    }

    // ACTUALIZAR ESTUDIANTE

    @Override
    public void actualizar(Estudiante estudiante) {

        String sql =
                "UPDATE estudiantes SET " +
                        "nombre = ?, " +
                        "rut = ?, " +
                        "curso = ?, " +
                        "correo = ? " +
                        "WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getRut());
            statement.setString(3, estudiante.getCurso());
            statement.setString(4, estudiante.getCorreo());
            statement.setInt(5, estudiante.getId());

            statement.executeUpdate();

            System.out.println(
                    "Estudiante actualizado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar estudiante."
            );

            e.printStackTrace();
        }
    }


    // ELIMINAR ESTUDIANTE


    @Override
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM estudiantes WHERE id = ?";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Estudiante eliminado correctamente."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar estudiante."
            );

            e.printStackTrace();
        }

        return false;
    }

    // CONVERTIR RESULTSET EN OBJETO ESTUDIANTE

    private Estudiante convertirEstudiante(
            ResultSet resultado) throws SQLException {

        Estudiante estudiante = new Estudiante();

        estudiante.setId(
                resultado.getInt("id")
        );

        estudiante.setNombre(
                resultado.getString("nombre")
        );

        estudiante.setRut(
                resultado.getString("rut")
        );

        estudiante.setCurso(
                resultado.getString("curso")
        );

        estudiante.setCorreo(
                resultado.getString("correo")
        );

        return estudiante;
    }
}