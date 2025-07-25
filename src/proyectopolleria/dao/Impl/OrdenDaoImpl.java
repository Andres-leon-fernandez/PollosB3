/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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
            JOptionPane.showMessageDialog(null,
                    "Error al listar ordenes del pedido: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            throw new DaoException("Error al listar ordenes por pedido", e);
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
                JOptionPane.showMessageDialog(null,
                        "No se pudo registrar la orden",
                        "Error de Registro",
                        JOptionPane.ERROR_MESSAGE);
                throw new DaoException("No se pudo insertar la orden");
            }

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar la orden: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
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
            if (stat.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró la orden para eliminar",
                        "Error de Eliminación",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar la orden: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null,
                    "Error al listar las órdenes: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            throw new DaoException("Error al listar ordenes", e);
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

    @Override
    public List<Orden> obtenerOrdenesPorPedido(int pedidoId) throws DaoException {
        List<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT o.*, p.nombre AS nombreProducto "
                + "FROM orden o "
                + "JOIN producto p ON o.producto_id = p.id "
                + "WHERE o.pedido_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Orden orden = new Orden();
                    orden.setId(rs.getInt("id"));
                    orden.setCantidad(rs.getInt("cantidad"));
                    orden.setSubtotal(rs.getDouble("subtotal"));
                    orden.setNombreProducto(rs.getString("nombreProducto")); // debes tener este atributo
                    ordenes.add(orden);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener las rdenes del pedido", e);
        }

        return ordenes;
    }

    public void eliminarOrdenesPorPedido(int pedidoId) throws DaoException {
        String sql = "DELETE FROM orden WHERE pedido_id = ?";

        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setInt(1, pedidoId);
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar las órdenes del pedido: " + e.getMessage(), e);
        }

    }

    public boolean validarInsumosDisponiblesPorOrden(int idProducto, int cantidad) throws DaoException {
        String sql = "SELECT MIN(CASE WHEN (i.stock - (ri.cantidad * ?)) < i.stock_min THEN 0 ELSE 1 END) cantidad "
                + "  FROM receta r "
                + "  JOIN receta_insumo ri "
                + "    ON ri.receta_id = r.id"
                + "  JOIN insumo i "
                + "    ON ri.insumo_id = i.id"
                + " WHERE r.producto_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad") == 1;
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener las ordenes del pedido", e);
        }
        return true;
    }

    @Override
    public void actualizarInsumosByOrden(int idOrden, String tipo) throws DaoException {
        int identificador;
        String sql_1 = "SELECT ri.insumo_id      "
                + "FROM orden o                   "
                + "JOIN receta r                    "
                + "ON r.producto_id = o.producto_id      "
                + "JOIN receta_insumo ri        "
                + "ON ri.receta_id = r.id        "
                + "WHERE o.id = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql_1)) {
            stat.setInt(1, idOrden);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    identificador = rs.getInt("insumo_id");
                    String sql = "UPDATE insumo i "
                            + "SET i.stock = (SELECT CASE WHEN '1' = ? THEN  "
                            + "      i.stock - (ri.cantidad * o.cantidad)  "
                            + "       ELSE  "
                            + "      i.stock + (ri.cantidad * o.cantidad) END resultado "
                            + "     FROM orden o  "
                            + "                 JOIN receta r "
                            + "                   ON r.producto_id = o.producto_id "
                            + "     JOIN receta_insumo ri "
                            + "       ON ri.receta_id = r.id "
                            + "    WHERE o.id = ? AND ri.insumo_id = i.id) "
                            + "WHERE i.id = ?";
                    try (PreparedStatement stat1 = conn.prepareStatement(sql)) {
                        stat1.setString(1, tipo);
                        stat1.setInt(2, idOrden);
                        stat1.setInt(3, identificador);
                        stat1.executeUpdate();
                    } catch (SQLException e) {
                        throw new DaoException("Error al modificar el insumo: " + e.getMessage(), e);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al modificar el insumo: " + e.getMessage(), e);
        }

    }

    public void actualizarTotalPedido(int pedidoId) throws DaoException {
        String sql = "UPDATE pedido p "
                + "SET p.total = (SELECT SUM(o.subtotal) "
                + "                 FROM orden o WHERE o.pedido_id = p.id) "
                + "WHERE p.id = ?";

        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setInt(1, pedidoId);
            if (stat.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo actualizar el total del pedido",
                        "Error de Actualización",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al actualizar el total del pedido: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            throw new DaoException("Error al actualizar el monto total del pedido", e);
        }
    }
}
