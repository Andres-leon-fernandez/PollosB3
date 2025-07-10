package proyectopolleria.model;

public class DetalleReceta {

    private Integer idProducto;
    private Integer idReceta;
    private double cantidad;

    public DetalleReceta() {
    }

    public DetalleReceta(Integer idProducto, Integer idReceta, double cantidad) {
        this.idProducto = idProducto;
        this.idReceta = idReceta;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return idProducto;
    }

    public void setId(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

}
