package modelo;

public class Estudiante extends Persona {

    private int id;
    private String curso;

    public Estudiante() {
    }

    public Estudiante(int id, String nombre, String rut, String curso, String correo) {
        super(nombre, rut, correo);
        this.id = id;
        this.curso = curso;
    }

    public int getId() {return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return getNombre();
    }


}