package controlador;

import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import modelo.Libro;

import java.util.List;

public class ControladorLibro {

    private LibroDAO libroDAO;

    public ControladorLibro() {
        libroDAO = new LibroDAOImpl();
    }

    // Guarda un libro
    public void guardar(Libro libro) {

        libroDAO.guardar(libro);
    }

    // Busca un libro por ID
    public Libro buscarPorId(int id) {

        return libroDAO.buscarPorId(id);
    }

    // Obtiene todos los libros
    public List<Libro> listar() {

        return libroDAO.listar();
    }

    // Actualiza un libro
    public void actualizar(Libro libro) {

        libroDAO.actualizar(libro);
    }

    // Elimina un libro
    public boolean eliminar(int id) {

        return libroDAO.eliminar(id);
    }
}