package proyectopolleria.model;

public class Mesero extends Trabajador {

    public Mesero(int id_trabajador, String Nombre, String Correo, String paswword) {
        super(id_trabajador, Nombre, Correo, paswword);
    }

    public String getTipo() {
        return "Mesero";
    }
}
