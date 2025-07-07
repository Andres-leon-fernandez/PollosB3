package proyectopolleria.dao.interfaces;

import java.util.List;

public interface Dao<T, K> {

    public void crear(T t);

    public void modificar(T t);

    public void eliminar(T t);

    List<T> listarTodos();

    public T obtener(K id);
}
