package proyectopolleria.model;

public class DetalleReceta {

    private Integer idInsumo;
    private Integer idReceta;
    private double cantidad;

    public DetalleReceta() {
    }

    public DetalleReceta(Integer idInsumo, Integer idReceta, double cantidad) {
        this.idInsumo = idInsumo;
        this.idReceta = idReceta;
        this.cantidad = cantidad;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
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
