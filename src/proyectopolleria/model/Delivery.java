package proyectopolleria.model;

public class Delivery extends Trabajador {

    private boolean Disponible;

    public Delivery(boolean Disponible, int id_trabajador, String Nombre, String Correo, String paswword) {
        super(id_trabajador, Nombre, Correo, paswword);
        this.Disponible = Disponible;
    }

    public boolean isDisponible() {
        return Disponible;
    }

    public void setDisponible(boolean Disponible) {
        this.Disponible = Disponible;
    }

    public String getTipo() {
        return "Delivery";
    }
}
