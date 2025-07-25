/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Cliente;

/**
 *
 * @author Andres
 */
public interface ClienteService {

    void registrarClienteLocal(Cliente cliente) throws DaoException;

    void registrarClienteDelivery(Cliente cliente) throws DaoException;

    void actualizarCliente(Cliente cliente) throws DaoException;

    void eliminarCliente(Cliente cliente) throws DaoException;

    Cliente obtenerPorId(int id) throws DaoException;
    
    Cliente buscarClientePorDocumento(String documento) throws DaoException;

    List<Cliente> listarTodos() throws DaoException;
}
