package proyectopolleria.model;

public class Cliente {

    private int Id;
    private String Nombre;
    private String Dni;
    private String Telefono;
    private String Direccion;
    private String Referencia;

    public Cliente() {
    }

    public Cliente(String Nombre, String Dni) {
        this.Nombre = Nombre;
        this.Dni = Dni;
    }

    public Cliente(String Nombre, String Dni, String Telefono, String Direccion, String Referencia) {
        this.Nombre = Nombre;
        this.Dni = Dni;
        this.Telefono = Telefono;
        this.Direccion = Direccion;
        this.Referencia = Referencia;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String Dni) {
        this.Dni = Dni;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }

}
