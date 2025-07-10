package proyectopolleria.model;

public class Receta {

    private Integer id;
    private Integer idProducto;

    public Receta() {
    }

    public Receta(Integer id, Integer idProducto) {
        this.id = id;
        this.idProducto = idProducto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

}
