package proyectopolleria.model;

public abstract class Trabajador {

    private int id_trabajador;
    private String Nombre;
    private String Correo;
    private String paswword;

    public Trabajador() {
    }

    public Trabajador(int id_trabajador, String Nombre, String Correo, String paswword) {
        this.id_trabajador = id_trabajador;
        this.Nombre = Nombre;
        this.Correo = Correo;
        this.paswword = paswword;
    }

    public int getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getPaswword() {
        return paswword;
    }

    public void setPaswword(String paswword) {
        this.paswword = paswword;
    }

}
