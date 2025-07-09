package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;

public interface Dao<T, K> {

    void crear(T t) throws DaoException;

    void modificar(T t) throws DaoException;

    void eliminar(T t) throws DaoException;

    List<T> listarTodos() throws DaoException;

    T obtener(K id) throws DaoException;
}
