package proyectopolleria.controller;

import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Pedido;
import proyectopolleria.service.interfaz.PedidoService;

/**
 *
 * @author Andres
 */
public class PedidoController {
    
    private PedidoService pedidoService;
    
    public void eliminarPedido(Pedido pedido) {
        try {
            pedidoService.eliminarPedido(pedido);
            System.out.println("?? Pedido eliminado.");
        } catch (DaoException e) {
            System.err.println("? Error al eliminar trabajador: " + e.getMessage());
        }
    }
}
