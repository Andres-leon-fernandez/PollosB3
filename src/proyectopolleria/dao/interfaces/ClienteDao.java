/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.interfaces;

import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Cliente;

/**
 *
 * @author andres
 */
public interface ClienteDao extends Dao<Cliente, Integer> {

    Cliente obtenerPorDni(String dni) throws DaoException;
    
    void crearLocal(Cliente t) throws DaoException;
}
