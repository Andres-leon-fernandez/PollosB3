package proyectopolleria.model;

public class Orden {

    private Integer id;
    private Producto producto;
    private Pedido pedido;
    private int cantidad;
    private double subtotal;

    public Orden() {
    }

    public Orden(Producto producto, int cantidad, double subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = cantidad * producto.getPrecio();
    }

    public Orden(Integer id, Producto producto, Pedido pedido, int cantidad, double subtotal) {
        this.id = id;
        this.producto = producto;
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
