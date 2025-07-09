package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Orden;
import proyectopolleria.model.Pedido;

public interface PedidoDao extends Dao<Pedido, Integer> {

    List<Orden> listarPorPedido(Pedido pedido) throws DaoException;

    void eliminarPorPedido(Integer pedidoId) throws DaoException;
}
