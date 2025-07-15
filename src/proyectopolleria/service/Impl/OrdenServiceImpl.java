/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.OrdenDao;
import proyectopolleria.model.Orden;
import proyectopolleria.service.interfaz.OrdenService;

/**
 *
 * @author LAB-USR-ATE
 */
public class OrdenServiceImpl implements OrdenService {

    private OrdenDao dao;

    public OrdenServiceImpl(OrdenDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarOrden(Orden o) throws DaoException {
        dao.crear(o);
    }

    @Override
    public void actualizarOrden(Orden o) throws DaoException {
        dao.modificar(o);
    }

    @Override
    public void eliminarOrden(Orden o) throws DaoException {
        dao.eliminar(o);
    }

    @Override
    public Orden obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Orden> listarTodos() throws DaoException {
        return dao.listarTodos();
    }

    @Override
    public List<Orden> listarPorPedido(int pedidoId) throws DaoException {
        return dao.listarPorPedido(pedidoId);

    }

}
