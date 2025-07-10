package proyectopolleria.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    public enum tipoPedido {
        SALON,
        DELIVERY
    }

    private Integer id;
    private Integer idCliente;
    private Integer idMOZO;
    private Integer idDELIVERY;
    private LocalDateTime fechaHora;
    private tipoPedido tipo;
    private List<Integer> idOrdenes;
    private double total;

    public Pedido() {
    }

    public Pedido(Integer idCliente, Integer idMOZO, Integer idDELIVERY, LocalDateTime fechaHora, tipoPedido tipo, List<Integer> idOrdenes, double total) {
        this.idCliente = idCliente;
        this.idMOZO = idMOZO;
        this.idDELIVERY = idDELIVERY;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.idOrdenes = idOrdenes;
        this.total = total;
    }

    public Pedido(Integer id, Integer idCliente, Integer idMOZO, Integer idDELIVERY, LocalDateTime fechaHora, tipoPedido tipo, List<Integer> idOrdenes, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.idMOZO = idMOZO;
        this.idDELIVERY = idDELIVERY;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.idOrdenes = idOrdenes;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMOZO() {
        return idMOZO;
    }

    public void setIdMOZO(Integer idMOZO) {
        this.idMOZO = idMOZO;
    }

    public Integer getIdDELIVERY() {
        return idDELIVERY;
    }

    public void setIdDELIVERY(Integer idDELIVERY) {
        this.idDELIVERY = idDELIVERY;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public tipoPedido getTipo() {
        return tipo;
    }

    public void setTipo(tipoPedido tipo) {
        this.tipo = tipo;
    }

    public List<Integer> getIdOrdenes() {
        return idOrdenes;
    }

    public void setIdOrdenes(List<Integer> idOrdenes) {
        this.idOrdenes = idOrdenes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
