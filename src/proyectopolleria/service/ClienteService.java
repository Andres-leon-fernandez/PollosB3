/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Cliente;

/**
 *
 * @author Andres
 */
public interface ClienteService {

    void registrarCliente(Cliente cliente) throws DaoException;

    void actualizarCliente(Cliente cliente) throws DaoException;

    void eliminarCliente(Cliente cliente) throws DaoException;

    Cliente obtenerPorId(int id) throws DaoException;

    List<Cliente> listarTodos() throws DaoException;
}
