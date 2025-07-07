
package proyectopolleria.model;

import java.time.LocalDateTime;
import java.util.List;


public class Pedido {
    private String idPedido;
    private Cliente cliente;
    private Mozo mozo;
    private LocalDateTime fecha;
    private String estado;
    private List<Orden> ordenes;

    public Pedido(String idPedido, Cliente cliente, Mozo mozo, LocalDateTime fecha, String estado, List<Orden> ordenes) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.mozo = mozo;
        this.fecha = fecha;
        this.estado = estado;
        this.ordenes = ordenes;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mozo getMozo() {
        return mozo;
    }

    public void setMozo(Mozo mozo) {
        this.mozo = mozo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }
    
    
}
