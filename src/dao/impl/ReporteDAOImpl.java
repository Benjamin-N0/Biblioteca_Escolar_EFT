package dao.impl;

import dao.ReporteDAO;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAOImpl implements ReporteDAO {

    private Connection conexion;

    public ReporteDAOImpl() {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<String[]> librosMasPrestados() {

        List<String[]> lista = new ArrayList<>();

        String sql =
                "SELECT l.titulo, COUNT(p.id) AS cantidad " +
                        "FROM prestamos p " +
                        "INNER JOIN libros l ON p.id_libro = l.id " +
                        "GROUP BY l.id, l.titulo " +
                        "ORDER BY cantidad DESC";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                String titulo = resultado.getString("titulo");
                String cantidad = String.valueOf(resultado.getInt("cantidad"));

                lista.add(new String[]{
                        titulo,
                        cantidad
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al obtener libros mas prestados."
            );
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<String[]> historialEstudiante(int idEstudiante) {

        List<String[]> lista = new ArrayList<>();

        String sql =
                "SELECT l.titulo, " +
                        "p.fecha_prestamo, " +
                        "p.fecha_devolucion, " +
                        "p.devuelto " +
                        "FROM prestamos p " +
                        "INNER JOIN libros l ON p.id_libro = l.id " +
                        "WHERE p.id_estudiante = ? " +
                        "ORDER BY p.fecha_prestamo DESC";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    String titulo = resultado.getString("titulo");

                    String fechaPrestamo = resultado.getString("fecha_prestamo");

                    String fechaDevolucion = resultado.getString("fecha_devolucion");

                    String devuelto = resultado.getBoolean("devuelto")
                                    ? "Si"
                                    : "No";

                    lista.add(new String[]{
                            titulo,
                            fechaPrestamo,
                            fechaDevolucion,
                            devuelto
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al obtener historial del estudiante."
            );

            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> librosEnPrestamo() {

        List<String[]> lista = new ArrayList<>();

        String sql =
                "SELECT e.nombre, " +
                        "l.titulo, " +
                        "p.fecha_prestamo, " +
                        "p.fecha_devolucion " +
                        "FROM prestamos p " +
                        "INNER JOIN estudiantes e " +
                        "ON p.id_estudiante = e.id " +
                        "INNER JOIN libros l " +
                        "ON p.id_libro = l.id " +
                        "WHERE p.devuelto = FALSE " +
                        "ORDER BY p.fecha_devolucion";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                String estudiante = resultado.getString("nombre");
                String libro = resultado.getString("titulo");
                String fechaPrestamo = resultado.getString("fecha_prestamo");
                String fechaDevolucion = resultado.getString("fecha_devolucion");

                lista.add(new String[]{
                        estudiante,
                        libro,
                        fechaPrestamo,
                        fechaDevolucion
                });
            }
        } catch (Exception e) {

            System.out.println(
                    "Error al obtener libros en prestamo."
            );
            e.printStackTrace();
        }
        return lista;
    }
}