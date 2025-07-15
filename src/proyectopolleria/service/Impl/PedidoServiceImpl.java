
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.PedidoDao;
import proyectopolleria.model.Pedido;
import proyectopolleria.service.interfaz.PedidoService;


public class PedidoServiceImpl implements PedidoService{
    private PedidoDao dao;

    public PedidoServiceImpl(PedidoDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarPedido(Pedido p) throws DaoException {
        dao.crear(p);
    }

    @Override
    public void actualizarPedido(Pedido p) throws DaoException {
        dao.modificar(p);
    }

    @Override
    public void eliminarPedido(Pedido p) throws DaoException {
        dao.eliminar(p);
    }

    @Override
    public Pedido obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Pedido> listarTodos() throws DaoException {
        return dao.listarTodos();
    }
    
}
