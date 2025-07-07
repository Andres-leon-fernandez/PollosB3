package proyectopolleria.model;

public class Mozo {
    private String apellidos;
    private String nombres;
    private String dni;

    public Mozo(String apellidos, String nombres, String dni) {
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public String getDni() {
        return dni;
    }
}