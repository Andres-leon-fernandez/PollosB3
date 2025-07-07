package proyectopolleria.model;

public class Administrador extends Trabajador {

    public Administrador(int id_trabajador, String Nombre, String Correo, String paswword) {
        super(id_trabajador, Nombre, Correo, paswword);
    }

    public String getTipo() {
        return "Administrador";
    }

}
