package proyectopolleria.model;

public class Orden {

    private Integer id;
    private Integer idProducto;
    private Integer idPedido;
    private int cantidad;
    private double subtotal;

    public Orden() {
    }

    public Orden(Integer idProducto, Integer idPedido, int cantidad, double subtotal) {
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Orden(Integer id, Integer idProducto, Integer idPedido, int cantidad, double subtotal) {
        this.id = id;
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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
