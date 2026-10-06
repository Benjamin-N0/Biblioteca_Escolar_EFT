package controlador;

import dao.PrestamoDAO;
import dao.impl.PrestamoDAOImpl;
import modelo.Prestamo;

import java.util.List;

public class ControladorPrestamo {

    private PrestamoDAO prestamoDAO;

    public ControladorPrestamo() {

        prestamoDAO = new PrestamoDAOImpl();
    }

    public void guardar(Prestamo prestamo) {

        prestamoDAO.guardar(prestamo);
    }

    public Prestamo buscarPorId(int id) {

        return prestamoDAO.buscarPorId(id);
    }

    public List<Prestamo> listar() {

        return prestamoDAO.listar();
    }

    public void actualizar(Prestamo prestamo) {

        prestamoDAO.actualizar(prestamo);
    }

    public boolean eliminar(int id) {

        return prestamoDAO.eliminar(id);
    }
}