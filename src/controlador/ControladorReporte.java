package controlador;

import dao.ReporteDAO;
import dao.impl.ReporteDAOImpl;

import java.util.List;

public class ControladorReporte {

    private ReporteDAO reporteDAO;

    public ControladorReporte() {
        reporteDAO = new ReporteDAOImpl();
    }

    public List<String[]> librosMasPrestados() {
        return reporteDAO.librosMasPrestados();
    }

    public List<String[]> historialEstudiante(int idEstudiante) {
        return reporteDAO.historialEstudiante(idEstudiante);
    }

    public List<String[]> librosEnPrestamo() {
        return reporteDAO.librosEnPrestamo();
    }
}