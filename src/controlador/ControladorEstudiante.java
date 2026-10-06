package controlador;

import dao.EstudianteDAO;
import dao.impl.EstudianteDAOImpl;
import modelo.Estudiante;

import java.util.List;

public class ControladorEstudiante {

    private EstudianteDAO estudianteDAO;

    public ControladorEstudiante() {

        estudianteDAO = new EstudianteDAOImpl();
    }

    public void guardar(Estudiante estudiante) {

        estudianteDAO.guardar(estudiante);
    }

    public Estudiante buscarPorId(int id) {

        return estudianteDAO.buscarPorId(id);
    }

    public List<Estudiante> listar() {

        return estudianteDAO.listar();
    }

    public void actualizar(Estudiante estudiante) {

        estudianteDAO.actualizar(estudiante);
    }

    public boolean eliminar(int id) {

        return estudianteDAO.eliminar(id);
    }
}