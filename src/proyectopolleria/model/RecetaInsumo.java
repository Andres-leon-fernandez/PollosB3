/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.model;

public class RecetaInsumo {

    private Integer idReceta;
    private Integer idInsumo;
    private double cantidad;

    public RecetaInsumo() {
    }

    public RecetaInsumo(Integer idReceta, Integer idInsumo, double cantidad) {
        this.idReceta = idReceta;
        this.idInsumo = idInsumo;
        this.cantidad = cantidad;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

}
