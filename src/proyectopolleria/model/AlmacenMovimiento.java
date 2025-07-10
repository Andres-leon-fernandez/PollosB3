package proyectopolleria.model;

import java.time.LocalDateTime;

public class AlmacenMovimiento {

    public enum TipoMovimiento {
        INGRESO,
        SALIDA
    }

    private Integer id;
    private Integer idInsumo;
    private TipoMovimiento tipoMovimiento;
    private double cantidad;
    private LocalDateTime fechaHora;

    public AlmacenMovimiento() {
    }

    public AlmacenMovimiento(Integer idInsumo, TipoMovimiento tipoMovimiento, double cantidad, LocalDateTime fechaHora) {
        this.idInsumo = idInsumo;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
    }

    public AlmacenMovimiento(Integer id, Integer idInsumo, TipoMovimiento tipoMovimiento, double cantidad, LocalDateTime fechaHora) {
        this.id = id;
        this.idInsumo = idInsumo;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

}
