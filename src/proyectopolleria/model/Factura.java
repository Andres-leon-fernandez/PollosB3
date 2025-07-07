
package proyectopolleria.model;

import java.time.LocalDateTime;

public class Factura {
    private int idFactura;
    private Pedido pedido;
    private LocalDateTime fecha;
    private double total;

    public Factura() {
    }

    public Factura(int idFactura, Pedido pedido, LocalDateTime fecha, double total) {
        this.idFactura = idFactura;
        this.pedido = pedido;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
