package modelo;

public class Usuario extends Persona {

    private int id;
    private String contrasenia;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String rut, String correo, String contrasenia, String rol) {
        super(nombre, rut, correo);
        this.id = id;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


}