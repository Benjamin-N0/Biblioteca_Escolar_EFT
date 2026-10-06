package dao;

import java.util.List;

public interface ReporteDAO {

    List<String[]> librosMasPrestados();

    List<String[]> historialEstudiante(int idEstudiante);

    List<String[]> librosEnPrestamo();
}