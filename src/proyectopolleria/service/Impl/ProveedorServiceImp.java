package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.service.interfaz.ProveedorService;
import proyectopolleria.dao.interfaces.ProveedorDao;
import proyectopolleria.model.Proveedor;

public class ProveedorServiceImp implements ProveedorService {

    private ProveedorDao dao;

    public ProveedorServiceImp(ProveedorDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarProveedor(Proveedor proveedor) throws DaoException {
        dao.crear(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) throws DaoException {
        dao.modificar(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) throws DaoException {
        dao.eliminar(proveedor);
    }

    @Override
    public Proveedor obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Proveedor> listarTodos() throws DaoException {
        return dao.listarTodos();
    }

}
