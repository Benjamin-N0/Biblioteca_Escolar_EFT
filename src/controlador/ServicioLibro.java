package controlador;

import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import modelo.Libro;

public class ServicioLibro {

    private LibroDAO libroDAO;

    public ServicioLibro() {

        libroDAO = new LibroDAOImpl();
    }

    public Libro buscarLibro(int id) {

        return libroDAO.buscarPorId(id);
    }

    public void actualizarLibro(Libro libro) {

        libroDAO.actualizar(libro);
    }

    public boolean hayStock(int idLibro) {

        Libro libro = libroDAO.buscarPorId(idLibro);

        if (libro == null) {

            return false;
        }

        return libro.getStock() > 0;
    }

    public void descontarStock(int idLibro) {

        Libro libro = libroDAO.buscarPorId(idLibro);

        if (libro == null) {
            return;
        }

        int stockActual =
                libro.getStock();

        if (stockActual <= 0) {
            return;
        }

        libro.setStock(stockActual - 1);
        libroDAO.actualizar(libro);
    }

    public void aumentarStock(int idLibro) {

        Libro libro = libroDAO.buscarPorId(idLibro);

        if (libro == null) {
            return;
        }

        int stockActual = libro.getStock();

        libro.setStock(stockActual + 1);

        libroDAO.actualizar(libro);
    }
}