/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.model;

/**
 *
 * @author andres
 */
public class Trabajador {

    public enum TipoTrabajador {
        ADMIN,
        MOZO,
        DELIVERY
    }

    private Integer id;
    private String user;
    private String password;
    private String dni;
    private String nombre;
    private String correo;
    private String telefono;
    private boolean activo;
    private TipoTrabajador tipoTrabajador;
    private boolean disponible;

    public Trabajador() {
    }

    public Trabajador(String password, String dni, String nombre, String correo, String telefono, TipoTrabajador tipoTrabajador) {
        this.dni = dni;
        this.user = generarUsuarioPorDNI(dni);
        this.password = password;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = true;
        this.tipoTrabajador = tipoTrabajador;
        this.disponible = (tipoTrabajador == TipoTrabajador.DELIVERY);
    }

    public Trabajador(Integer id, String user, String password, String dni, String nombre, String correo, String telefono, boolean activo, TipoTrabajador tipoTrabajador, boolean disponible) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo;
        this.tipoTrabajador = tipoTrabajador;
        this.disponible = disponible;
    }

    private String generarUsuarioPorDNI(String dni) {
        if (dni != null && dni.length() >= 4) {
            return "PB3" + dni.substring(dni.length() - 4);
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoTrabajador getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
