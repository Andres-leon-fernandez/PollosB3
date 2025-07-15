/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.AlmacenMovimiento;

/**
 *
 * @author Andres
 */
public interface AlmacenService {

    void registrarCliente(AlmacenMovimiento aMov) throws DaoException;

    void actualizarCliente(AlmacenMovimiento aMov) throws DaoException;

    void eliminarCliente(AlmacenMovimiento aMov) throws DaoException;

    AlmacenMovimiento obtenerPorId(int id) throws DaoException;

    List<AlmacenMovimiento> listarTodos() throws DaoException;
}
