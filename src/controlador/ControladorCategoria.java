package controlador;

import dao.CategoriaDAO;
import dao.impl.CategoriaDAOImpl;
import modelo.Categoria;

import java.util.List;

public class ControladorCategoria {

    private CategoriaDAO categoriaDAO;

    public ControladorCategoria() {

        categoriaDAO = new CategoriaDAOImpl();
    }

    public List<Categoria> listar() {

        return categoriaDAO.listar();
    }

    public void guardar(Categoria categoria) {

        categoriaDAO.guardar(categoria);
    }

    public Categoria buscarPorId(int id) {

        return categoriaDAO.buscarPorId(id);
    }

    public void actualizar(Categoria categoria) {

        categoriaDAO.actualizar(categoria);
    }

    public boolean eliminar(int id) {

        return categoriaDAO.eliminar(id);
    }
}