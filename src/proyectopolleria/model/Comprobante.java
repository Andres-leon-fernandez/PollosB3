package proyectopolleria.model;

import java.time.LocalDateTime;
import java.util.List;

public class Comprobante {

    private Integer id;
    private Pedido pedido;
    private LocalDateTime fechaHora;
    private double total;
    private String tipoComprobante;
    private String metodoPago;

    public Comprobante() {
    }

    public Comprobante(Pedido pedido, String tipoComprobante, String metodoPago) {
        this.pedido = pedido;
        this.fechaHora = LocalDateTime.now();
        this.total = CalcularTotal(pedido);
        this.tipoComprobante = tipoComprobante.toUpperCase();
        this.metodoPago = metodoPago.toUpperCase();
    }

    public Comprobante(Integer id, Pedido pedido, LocalDateTime fechaHora, double total, String tipoComprobante, String metodoPago) {
        this.id = id;
        this.pedido = pedido;
        this.fechaHora = fechaHora;
        this.total = total;
        this.tipoComprobante = tipoComprobante;
        this.metodoPago = metodoPago;
    }

    private double CalcularTotal(Pedido p) {
        return p.getOrdenes().stream().mapToDouble(Orden::getSubtotal).sum();
    }

    public Integer getId() {
        return id;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
