package dao;

import modelo.Categoria;

import java.util.List;

public interface CategoriaDAO {

    // Guardar una categoria
    void guardar(Categoria categoria);

    // Buscar una categoria por ID
    Categoria buscarPorId(int id);

    // Obtener todas las categorias
    List<Categoria> listar();

    // Actualizar una categoria
    void actualizar(Categoria categoria);

    // Eliminar una categoria
    boolean eliminar(int id);
}