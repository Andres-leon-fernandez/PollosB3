package proyectopolleria.model;

public class Insumo {

    public enum Unidad {
        KILOGRAMOS, // Para carnes, papas, etc.
        LITROS, // Para aceites, bebidas, salsas l�quidas, etc.
        UNIDADES, // Para huevos, limones, panes, etc.
        GRAMOS, // Para especias, harinas en peque�as cantidades, etc.
        MILILITROS, // Para aderezos o esencias en peque�as cantidades.
        ATADOS, // Para hierbas arom�ticas como el or�gano o el culantro.
        SACOS, // Para papas, arroz, carb�n, etc. en grandes vol�menes.
        BOTELLAS, // Para gaseosas, cervezas, licores, etc.
        PORCIONES
    }

    private Integer id;
    private String nombre;
    private double stock;
    private double stockMin;
    private boolean disponible;
    private Unidad unidad;
    private double precioUnitario;
    private Integer idProveedor;

    public Insumo() {
    }

    public Insumo(Integer id, String nombre, double stock, double stockMin, boolean disponible, Unidad unidad, double precioUnitario, Integer idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMin = stockMin;
        this.disponible = disponible;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
        this.idProveedor = idProveedor;
    }

    public Insumo(Integer id, String nombre, double stock, double stockMin, Unidad unidad, double precioUnitario, Integer idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMin = stockMin;
        this.disponible = true;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
        this.idProveedor = idProveedor;
    }

    public Insumo(String nombre, double stock, double stockMin, boolean disponible, Unidad unidad, double precioUnitario, Integer idProveedor) {
        this.nombre = nombre;
        this.stock = stock;
        this.stockMin = stockMin;
        this.disponible = disponible;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
        this.idProveedor = idProveedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

}
