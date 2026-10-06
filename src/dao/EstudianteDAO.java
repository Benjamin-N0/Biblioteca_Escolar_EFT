package dao;

import modelo.Estudiante;

import java.util.List;

public interface EstudianteDAO {

    // Guardar un estudiante
    void guardar(Estudiante estudiante);

    // Buscar un estudiante por ID
    Estudiante buscarPorId(int id);

    // Obtener todos los estudiantes
    List<Estudiante> listar();

    // Actualizar un estudiante
    void actualizar(Estudiante estudiante);

    // Eliminar un estudiante
    boolean eliminar(int id);
}
