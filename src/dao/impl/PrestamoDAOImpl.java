package dao.impl;

import dao.PrestamoDAO;
import modelo.Prestamo;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {

    private Connection conexion;

    public PrestamoDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // GUARDAR PRESTAMO

    @Override
    public void guardar(Prestamo prestamo) {

        String sql =
                "INSERT INTO prestamos " +
                        "(id_estudiante, id_libro, fecha_prestamo, " +
                        "fecha_devolucion, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, prestamo.getIdEstudiante()
            );

            statement.setInt(2, prestamo.getIdLibro()
            );

            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())
            );

            statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion())
            );

            statement.setBoolean(5, prestamo.isDevuelto()
            );

            statement.executeUpdate();

            ResultSet resultado = statement.getGeneratedKeys();

            if (resultado.next()) {

                prestamo.setId(
                        resultado.getInt(1)
                );
            }

            System.out.println(
                    "Prestamo guardado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar prestamo."
            );
            e.printStackTrace();
        }
    }

    // BUSCAR PRESTAMO POR ID

    @Override
    public Prestamo buscarPorId(int id) {

        String sql = "SELECT * FROM prestamos WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                return convertirPrestamo(resultado);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar prestamo."
            );

            e.printStackTrace();
        }
        return null;
    }

    // LISTAR PRESTAMOS

    @Override
    public List<Prestamo> listar() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM prestamos";

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                prestamos.add(
                        convertirPrestamo(resultado)
                );
            }

        } catch (SQLException e) {

            System.out.println("Error al listar prestamos."
            );

            e.printStackTrace();
        }
        return prestamos;
    }

    // ACTUALIZAR PRESTAMO

    @Override
    public void actualizar(Prestamo prestamo) {

        String sql =
                "UPDATE prestamos SET " +
                        "id_estudiante = ?, " +
                        "id_libro = ?, " +
                        "fecha_prestamo = ?, " +
                        "fecha_devolucion = ?, " +
                        "devuelto = ? " +
                        "WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, prestamo.getIdEstudiante()
            );

            statement.setInt(2, prestamo.getIdLibro()
            );

            statement.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())
            );

            statement.setDate(4, Date.valueOf(prestamo.getFechaDevolucion())
            );

            statement.setBoolean(5, prestamo.isDevuelto()
            );

            statement.setInt(6, prestamo.getId()
            );

            statement.executeUpdate();

            System.out.println("Prestamo actualizado correctamente."
            );

        } catch (SQLException e) {

            System.out.println("Error al actualizar prestamo."
            );
            e.printStackTrace();
        }
    }

    // ELIMINAR PRESTAMO

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM prestamos WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Prestamo eliminado correctamente."
                );
                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar prestamo."
            );

            e.printStackTrace();
        }
        return false;
    }

    // CONVERTIR RESULTSET EN OBJETO PRESTAMO

    private Prestamo convertirPrestamo(
            ResultSet resultado) throws SQLException {

        Prestamo prestamo = new Prestamo();

        prestamo.setId(
                resultado.getInt("id")
        );

        prestamo.setIdEstudiante(
                resultado.getInt("id_estudiante")
        );

        prestamo.setIdLibro(
                resultado.getInt("id_libro")
        );

        prestamo.setFechaPrestamo(
                resultado.getDate("fecha_prestamo")
                        .toLocalDate()
        );

        prestamo.setFechaDevolucion(
                resultado.getDate("fecha_devolucion")
                        .toLocalDate()
        );

        prestamo.setDevuelto(
                resultado.getBoolean("devuelto")
        );
        return prestamo;
    }
}