package dao;

import modelo.Prestamo;

import java.util.List;

public interface PrestamoDAO {

    // Guardar un prestamo
    void guardar(Prestamo prestamo);

    // Buscar un prestamo por ID
    Prestamo buscarPorId(int id);

    // Obtener todos los prestamos
    List<Prestamo> listar();

    // Actualizar un prestamo
    void actualizar(Prestamo prestamo);

    // Eliminar un prestamo
    boolean eliminar(int id);
}