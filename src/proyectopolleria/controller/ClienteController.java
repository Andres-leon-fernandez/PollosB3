/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.controller;

import java.util.List;
import proyectopolleria.dao.interfaces.DaoManager;
import proyectopolleria.model.Cliente;
import proyectopolleria.service.interfaz.ClienteService;
import java.sql.Connection;
import proyectopolleria.dao.Impl.DaoManagerImpl;
import proyectopolleria.service.Impl.ClienteServiceImpl;

/**
 *
 * @author Andres
 */
public class ClienteController {

    private ClienteService srvCli;

    public ClienteController(Connection conn) {
        DaoManager daoManager = new DaoManagerImpl(conn);
        this.srvCli = new ClienteServiceImpl(daoManager.getClienteDao());
    }

    public void registrarCliente(Cliente cli) {
        try {
            srvCli.registrarClienteDelivery(cli);
        } catch (Exception ex) {
            System.err.println("? Error al actualizar cliente: " + ex.getMessage());
        }

    }

    public void actualizarCliente(Cliente cli) {
        try {
            srvCli.actualizarCliente(cli);
            System.out.println("? Cliente actualizado.");
        } catch (Exception e) {
            System.err.println("? Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminarCliente(Cliente cli) {
        try {
            srvCli.eliminarCliente(cli);
            System.out.println("?? Cliente eliminado.");
        } catch (Exception e) {
            System.err.println("? Error al eliminar cliente: " + e.getMessage());
        }
    }

    public Cliente obtenerPorId(int id) {
        try {
            return srvCli.obtenerPorId(id);
        } catch (Exception e) {
            System.err.println("? Error al obtener cliente por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> listarClientes() {
        try {
            return srvCli.listarTodos();
        } catch (Exception e) {
            System.err.println("? Error al listar clientes: " + e.getMessage());
            return null;
        }
    }

}
