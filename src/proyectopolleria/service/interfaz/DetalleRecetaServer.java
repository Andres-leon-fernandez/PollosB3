/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.DetalleReceta;

/**
 *
 * @author andres
 */
public interface DetalleRecetaServer {

    void registrarCliente(DetalleReceta dRe) throws DaoException;

    void actualizarCliente(DetalleReceta dRe) throws DaoException;

    void eliminarCliente(DetalleReceta dRe) throws DaoException;

    DetalleReceta obtenerPorId(int id) throws DaoException;

    List<DetalleReceta> listarTodos() throws DaoException;
}
