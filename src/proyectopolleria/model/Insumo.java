package proyectopolleria.model;

public class Insumo {

    public enum Unidad {
        KG,
        LITROS,
        UNIDADES
    }

    private Integer id;
    private String nombre;
    private double stock;
    private double stockMin;
    private boolean disponible;
    private Unidad unidad;
    private double precioUnitario;
    private Proveedor proveedor;

    public Insumo() {
    }

    public Insumo(String nombre, double stock, double stockMin, Unidad unidad, double precioUnitario, Proveedor proveedor) {
        this.nombre = nombre;
        this.stock = stock;
        this.stockMin = stockMin;
        this.disponible = true;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
        this.proveedor = proveedor;
    }

    public Insumo(Integer id, String nombre, double stock, double stockMin, boolean disponible, Unidad unidad, double precioUnitario, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMin = stockMin;
        this.disponible = disponible;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
        this.proveedor = proveedor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getStockMin() {
        return stockMin;
    }

    public void setStockMin(double stockMin) {
        this.stockMin = stockMin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

}
