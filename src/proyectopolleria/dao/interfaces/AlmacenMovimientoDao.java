/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.AlmacenMovimiento;

/**
 *
 * @author andres
 */
public interface AlmacenMovimientoDao extends Dao<AlmacenMovimiento, Integer> {

    List<AlmacenMovimiento> obtenerMov(Integer id) throws DaoException;
}
