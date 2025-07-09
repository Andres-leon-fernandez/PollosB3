package proyectopolleria.model.previos;

public class Usuario {

    private String id;
    private String password;

    public Usuario(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

}
