/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Comprobante;

/**
 *
 * @author Andres
 */
public interface ComprobanteService {

    void registrarCliente(Comprobante comprobante) throws DaoException;

    Comprobante obtenerPorId(int id) throws DaoException;

    List<Comprobante> listarTodos() throws DaoException;
}
