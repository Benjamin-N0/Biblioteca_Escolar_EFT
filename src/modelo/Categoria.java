package modelo;

public class Categoria {

    private int id;
    private String nombre;

    // Constructor vacío
    public Categoria() {
    }

    // Constructor completo
    public Categoria(int id, String nombre) {

        this.id = id;
        this.nombre = nombre;
    }

    // Getter y Setter del ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter del nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Permite mostrar el nombre de la categoría
    // cuando utilicemos JComboBox
    @Override
    public String toString() {
        return nombre;
    }
}