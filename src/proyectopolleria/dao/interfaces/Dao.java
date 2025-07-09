package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;

public interface Dao<T, K> {

    public void crear(T t) throws DaoException;

    public void modificar(T t) throws DaoException;

    public void eliminar(T t) throws DaoException;

    List<T> listarTodos() throws DaoException;

    public T obtener(K id) throws DaoException;
}
