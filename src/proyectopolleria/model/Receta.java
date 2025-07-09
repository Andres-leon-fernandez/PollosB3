
package proyectopolleria.model;

/**
 *
 * @author andres
 */
public class Receta {
    private Integer id;
    private Producto producto;
    private Insumo insumo;
    private double cantidad;

    public Receta() {
    }

    public Receta(Producto producto, Insumo insumo, double cantidad) {
        this.producto = producto;
        this.insumo = insumo;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
