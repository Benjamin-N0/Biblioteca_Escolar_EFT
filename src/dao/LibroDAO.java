package dao;

import modelo.Libro;

import java.util.List;

public interface LibroDAO {

    // Guardar un libro
    void guardar(Libro libro);

    // Buscar un libro por ID
    Libro buscarPorId(int id);

    // Obtener todos los libros
    List<Libro> listar();

    // Actualizar un libro
    void actualizar(Libro libro);

    // Eliminar un libro
    boolean eliminar(int id);
}