/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.OrdenDao;
import proyectopolleria.model.Orden;

/**
 *
 * @author andres
 */
public class OrdenDaoImpl implements OrdenDao {

    private Connection conn;

    public OrdenDaoImpl(Connection conn) {
        this.conn = conn;
    }
    private final String INSERT = """
        INSERT INTO orden (producto_id, pedido_id, cantidad, subtotal)
        VALUES (?, ?, ?, ?)
    """;

    private final String SELECT_ALL = "SELECT * FROM orden";
    private final String SELECT_BY_ID = "SELECT * FROM orden WHERE id = ?";
    private final String SELECT_BY_PEDIDO = "SELECT * FROM orden WHERE pedido_id = ?";
    private final String DELETE = "DELETE FROM orden WHERE id = ?";

    @Override
    public List<Orden> listarPorPedido(int pedidoId) throws DaoException {
        List<Orden> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_PEDIDO)) {
            stat.setInt(1, pedidoId);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertir(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar órdenes por pedido", e);
        }
        return lista;
    }

    @Override
    public void crear(Orden o) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, o.getIdProducto());
            stat.setInt(2, o.getIdPedido());
            stat.setInt(3, o.getCantidad());
            stat.setDouble(4, o.getSubtotal());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se pudo insertar la orden");
            }

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al insertar orden", e);
        }
    }

    @Override
    public void modificar(Orden t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Orden t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(DELETE)) {
            stat.setInt(1, t.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar orden", e);
        }
    }

    private Orden convertir(ResultSet rs) throws SQLException {
        Orden o = new Orden();
        o.setId(rs.getInt("id"));
        o.setIdProducto(rs.getInt("producto_id"));
        o.setIdPedido(rs.getInt("pedido_id"));
        o.setCantidad(rs.getInt("cantidad"));
        o.setSubtotal(rs.getDouble("subtotal"));
        return o;
    }

    @Override
    public List<Orden> listarTodos() throws DaoException {
        List<Orden> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_ALL); ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                lista.add(convertir(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar órdenes", e);
        }
        return lista;
    }

    @Override
    public Orden obtener(Integer id) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return convertir(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener orden", e);
        }
        return null;
    }

}
