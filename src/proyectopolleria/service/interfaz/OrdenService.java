/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Orden;

/**
 *
 * @author LAB-USR-ATE
 */
public interface OrdenService {

    void registrarOrden(Orden o) throws DaoException;

    void actualizarOrden(Orden o) throws DaoException;

    void eliminarOrden(Orden o) throws DaoException;

    Orden obtenerPorId(int id) throws DaoException;

    List<Orden> listarTodos() throws DaoException;

    List<Orden> listarPorPedido(int pedidoId) throws DaoException;

    public String obtenerOrdenesPorPedido(int pedidoId) throws DaoException;

    boolean validarInsumosDisponiblesPorOrden(int idProductom, int cantidad) throws DaoException;

}
