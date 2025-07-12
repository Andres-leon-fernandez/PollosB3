
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ProductoDao;
import proyectopolleria.model.Producto;
import proyectopolleria.service.interfaz.ProductoService;

public class ProductoServiceImpl implements ProductoService{
    private ProductoDao dao;

    public ProductoServiceImpl(ProductoDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarProducto(Producto pro) throws DaoException {
        dao.crear(pro);
    }

    @Override
    public void actualizarProducto(Producto pro) throws DaoException {
        dao.modificar(pro);
    }

    @Override
    public void eliminarProducto(Producto pro) throws DaoException {
       dao.eliminar(pro);
    }

    @Override
    public Producto obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Producto> listarTodos() throws DaoException {
        return dao.listarTodos();
    }
}
