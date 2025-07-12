
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Proveedor;

public interface ProveedorService {
    void registrarProveedor(Proveedor proveedor) throws DaoException;

    void actualizarProveedor(Proveedor proveedor) throws DaoException;

    void eliminarProveedor(Proveedor proveedor) throws DaoException;

    Proveedor obtenerPorId(int id) throws DaoException;

    List<Proveedor> listarTodos() throws DaoException;
}
