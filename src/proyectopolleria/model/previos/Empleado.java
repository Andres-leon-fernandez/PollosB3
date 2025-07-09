package proyectopolleria.model.previos;

public class Empleado {

    public enum TipoEmpleado {
        MOZO,
        COCINA,
        CAJA
    }

    private String idEmpleado;
    private String nombreEmpleado;
    private TipoEmpleado tipo;

    public Empleado(String idEmpleado, String nombreEmpleado, TipoEmpleado tipo) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.tipo = tipo;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public TipoEmpleado getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpleado tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Empleado [ID: " + idEmpleado + ", Nombre: " + nombreEmpleado + ", Tipo: " + tipo + "]";
    }
}
