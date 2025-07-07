package proyectopolleria.model;

public class DetallesFactura {

    private int idDetallesFactura;
    private Factura factura;
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetallesFactura() {
    }

    public DetallesFactura(int idDetallesFactura, Factura factura, Producto producto, int cantidad, double subtotal) {
        this.idDetallesFactura = idDetallesFactura;
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getIdDetallesFactura() {
        return idDetallesFactura;
    }

    public void setIdDetallesFactura(int idDetallesFactura) {
        this.idDetallesFactura = idDetallesFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
