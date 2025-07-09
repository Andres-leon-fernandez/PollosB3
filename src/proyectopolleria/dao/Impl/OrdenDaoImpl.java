/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.OrdenDao;
import proyectopolleria.model.Orden;
import java.sql.*;
import java.util.ArrayList;
import proyectopolleria.dao.interfaces.ProductoDao;
import proyectopolleria.model.Producto;
public class OrdenDaoImpl implements OrdenDao {
    
    private Connection conn;

    private static final String INSERT
            = "INSERT INTO ordenes(pedido_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";

    private static final String DELETE_BY_PEDIDO
            = "DELETE FROM ordenes WHERE pedido_id=?";

    private static final String SELECT_BY_PEDIDO
            = "SELECT id, producto_id, cantidad, subtotal FROM ordenes WHERE pedido_id=?";

    @Override
    public void crear(Orden t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modificar(Orden t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Orden t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(DELETE_BY_PEDIDO)) {
            stat.setInt(1, t.getId());
            stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar órdenes por pedido", ex);
        }
    }

    @Override
    public List<Orden> listarTodos() throws DaoException {
        List<Orden> ordenes = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_PEDIDO)) {
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    Orden orden = new Orden();
                    orden.setId(rs.getInt("id"));
                    
                    // Obtener producto (necesitas ProductoDao)
                    ProductoDao productoDao = new ProductoDaoImpl(conn);
                    Producto producto = productoDao.obtener(rs.getInt("producto_id"));
                    orden.setProducto(producto);
                    
                    orden.setCantidad(rs.getInt("cantidad"));
                    orden.setSubtotal(rs.getDouble("subtotal"));
                    
                    ordenes.add(orden);
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al listar órdenes por pedido", ex);
        }
        return ordenes;
    }

    @Override
    public Orden obtener(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
