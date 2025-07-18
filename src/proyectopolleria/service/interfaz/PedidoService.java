/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Pedido;

/**
 *
 * @author Andres
 */
public interface PedidoService {

    void registrarPedido(Pedido p) throws DaoException;

    void actualizarPedido(Pedido p) throws DaoException;

    void eliminarPedido(Pedido p) throws DaoException;

    Pedido obtenerPorId(int id) throws DaoException;

    List<Pedido> listarTodos() throws DaoException;

    public Pedido crearPedidoTemporal() throws DaoException;
}
