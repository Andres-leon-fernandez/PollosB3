/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Orden;

/**
 *
 * @author andres
 */
public interface OrdenDao extends Dao<Orden, Integer> {

    List<Orden> listarPorPedido(int pedidoId) throws DaoException;
    public List<Orden> obtenerOrdenesPorPedido(int pedidoId) throws DaoException;
    boolean validarInsumosDisponiblesPorOrden(int idProducto, int cantidad) throws DaoException;
    void actualizarInsumosByOrden(int idOrden, String tipo) throws DaoException;
    void actualizarTotalPedido(int idPedido) throws DaoException;
    public void eliminarOrdenesPorPedido(int pedidoId) throws DaoException;
}
