package proyectopolleria.model;

import java.time.LocalDateTime;
import java.util.List;

public class Comprobante {

    public enum TipoComprobante {
        BOLETA,
        FACTURA
    }

    public enum MetodoPago {
        YAPE,
        PLIN,
        TARJETA,
        EFECTIVO
    }

    private Integer id;
    private Integer idPedido;
    private LocalDateTime fechaHora;
    private double total;
    private TipoComprobante tipoComprobante;
    private MetodoPago metodoPago;

    public Comprobante() {
    }

    public Comprobante(Integer id, Integer idPedido, LocalDateTime fechaHora, double total, TipoComprobante tipoComprobante, MetodoPago metodoPago) {
        this.id = id;
        this.idPedido = idPedido;
        this.fechaHora = fechaHora;
        this.total = total;
        this.tipoComprobante = tipoComprobante;
        this.metodoPago = metodoPago;
    }

    public Comprobante(Integer idPedido, LocalDateTime fechaHora, double total, TipoComprobante tipoComprobante, MetodoPago metodoPago) {
        this.idPedido = idPedido;
        this.fechaHora = fechaHora;
        this.total = total;
        this.tipoComprobante = tipoComprobante;
        this.metodoPago = metodoPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    
}
