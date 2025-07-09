package proyectopolleria.model;

public class Producto {

    private Integer id;
    private String descripcion;
    private double precio;
    private String categoria;
    private boolean activo;

    public Producto() {
    }

    public Producto(String descripcion, double precio, String categoria) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.activo = true;
    }

    public Producto(Integer id, String descripcion, double precio, String categoria, boolean activo) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
        throw new IllegalArgumentException("La descripción no puede estar vacía.");
    }
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
        throw new IllegalArgumentException("El precio no puede ser negativo.");
    }
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
