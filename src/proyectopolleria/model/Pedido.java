package proyectopolleria.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private Integer id;
    private Cliente cliente;
    private Trabajador MOZO;
    private Trabajador DELIVERY;
    private LocalDateTime fechaHora;
    private String tipo;
    private List<Orden> ordenes;
    private double total;

    public Pedido() {
    }

    public Pedido(Cliente cliente, Trabajador trabajador, String tipo, List<Orden> ordenes) {
        this.cliente = cliente;
        this.tipo = tipo.toUpperCase();
        this.fechaHora = LocalDateTime.now();
        this.ordenes = ordenes;

        if (this.tipo.equals("SALON")) {
            this.MOZO = trabajador;
            this.DELIVERY = null;
        } else if (this.tipo.equals("DELIVERY")) {
            this.MOZO = null;
            this.DELIVERY = trabajador;
        } else {
            throw new IllegalArgumentException("Tipo de pedido inválido: " + tipo);
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setMOZO(Trabajador MOZO) {
        this.MOZO = MOZO;
    }

    public void setDELIVERY(Trabajador DELIVERY) {
        this.DELIVERY = DELIVERY;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public Integer getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Trabajador getMOZO() {
        return MOZO;
    }

    public Trabajador getDELIVERY() {
        return DELIVERY;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

}
