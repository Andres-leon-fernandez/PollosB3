package proyectopolleria.DTO;

public class DetalleInsumoDTO {

    private int idInsumo;
    private String nombreInsumo;
    private double cantidadUsar;
    private String rucProveedor;
    private String nombreProveedor;

    public DetalleInsumoDTO(int idInsumo, String nombreInsumo, double cantidadUsar, String rucProveedor, String nombreProveedor) {
        this.idInsumo = idInsumo;
        this.nombreInsumo = nombreInsumo;
        this.cantidadUsar = cantidadUsar;
        this.rucProveedor = rucProveedor;
        this.nombreProveedor = nombreProveedor;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public double getCantidadUsar() {
        return cantidadUsar;
    }

    public void setCantidadUsar(double cantidadUsar) {
        this.cantidadUsar = cantidadUsar;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

}
