/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.dao.interfaces;

import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Producto;

/**
 *
 * @author andres
 */
public interface ProductoDao extends Dao<Producto, Integer>{
    String nombreProductoById(int id) throws DaoException;
}
