/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Producto;

/**
 *
 * @author Andres
 */
public interface ProductoService {

    void registrarProducto(Producto pro) throws DaoException;

    void actualizarProducto(Producto pro) throws DaoException;

    void eliminarProducto(Producto pro) throws DaoException;

    Producto obtenerPorId(int id) throws DaoException;

    List<Producto> listarTodos() throws DaoException;
    
    String nombreProductoById(int id) throws DaoException;
}
