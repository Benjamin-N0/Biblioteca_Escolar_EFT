package controlador;

import dao.PrestamoDAO;
import dao.impl.PrestamoDAOImpl;
import modelo.Prestamo;

public class ServicioPrestamo {

    private PrestamoDAO prestamoDAO;

    public ServicioPrestamo() {

        prestamoDAO = new PrestamoDAOImpl();
    }

    // Registra un prestamo en la base de datos
    public void registrarPrestamo(Prestamo prestamo) {

        prestamoDAO.guardar(prestamo);
    }

    // Busca un prestamo
    public Prestamo buscarPrestamo(int id) {

        return prestamoDAO.buscarPorId(id);
    }

    // Actualiza un prestamo
    public void actualizarPrestamo(Prestamo prestamo) {

        prestamoDAO.actualizar(prestamo);
    }

    // Elimina un prestamo
    public boolean eliminarPrestamo(int id) {

        return prestamoDAO.eliminar(id);
    }
}