package proyectopolleria.dao.Impl;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ComprobanteDao;
import proyectopolleria.model.Comprobante;
import proyectopolleria.model.Pedido;
import proyectopolleria.dao.interfaces.PedidoDao;

/**
 *
 * @author Andres
 */
public class ComprobanteDaoImpl implements ComprobanteDao {

    private Connection conn;
    private PedidoDao pedidoDao;

    public ComprobanteDaoImpl(Connection conn, PedidoDao pedidoDao) {
        this.conn = conn;
        this.pedidoDao = pedidoDao;
    }

    /*cambiar las consultas y los campos uwu*/
    static final String INSERT = "INSERT INTO comprobante(pedido_id, fecha_hora, total, tipo_comprobante, metodo_pago) VALUES (?, ?, ?, ?, ?)";
    //static final String UPDATE = "UPDATE comprobante SET pedido_id = ?, fecha_hora = ?, total = ?, tipo_comprobante = ?, metodo_pago = ? WHERE id = ?";
    static final String DELETE = "DELETE FROM comprobante WHERE id = ?";
    static final String SELECTID = "SELECT * FROM comprobante WHERE id = ?";
    static final String SELECTALL = "SELECT * FROM comprobante";

    @Override
    public void crear(Comprobante t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setInt(1, t.getPedido().getId());
            stat.setTimestamp(2, Timestamp.valueOf(t.getFechaHora()));
            stat.setDouble(3, t.getTotal());
            stat.setString(4, t.getTipoComprobante());
            stat.setString(5, t.getMetodoPago());

            stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }

            }
        }
    }

    @Override
    public void modificar(Comprobante t) throws DaoException {
        throw new UnsupportedOperationException("Modificar comprobantes no está permitido.");
    }

    @Override
    public void eliminar(Comprobante t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, t.getId());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("error en sql");
            }
        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }

            }

        }
    }

    private Comprobante convertidor(ResultSet rs) throws SQLException, DaoException {
        int pedidoId = rs.getInt("pedido_id");
        Pedido pedido = pedidoDao.obtener(pedidoId);
        return new Comprobante(rs.getInt("id"),
                pedido,
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                rs.getDouble("total"),
                rs.getString("tipo_comprobante"),
                rs.getString("metodo_pago"));
    }

    private void cerrarRecursos(ResultSet rs, PreparedStatement stat) throws DaoException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException ex) {
            throw new DaoException("Error cerrando recursos", ex);
        }
    }

    @Override
    public List<Comprobante> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Comprobante> comprobantes = new ArrayList<>();
        try {
            stat = conn.prepareStatement(SELECTALL);
            rs = stat.executeQuery();
            Map<Integer, Pedido> cachepedido = new HashMap<>();
            while (rs.next()) {
                int pedidoId = rs.getInt("pedido_id");
                Pedido pedido = cachepedido.get(pedidoId);

                if (pedido == null) {
                    pedido = pedidoDao.obtener(pedidoId);
                    cachepedido.put(pedidoId, pedido);
                }
                Comprobante comprobante = new Comprobante(
                        rs.getInt("id"),
                        pedido,
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getDouble("total"),
                        rs.getString("tipo_comprobante"),
                        rs.getString("metodo_pago")
                );
                comprobantes.add(comprobante);
            }
        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            cerrarRecursos(rs, stat);
        }
        return comprobantes;
    }

    @Override
    public Comprobante obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(SELECTID);
            stat.setInt(1, id);
            rs = stat.executeQuery();

            if (rs.next()) {
                return convertidor(rs);
            } else {
                throw new DaoException("error en sql");
            }

        } catch (SQLException ex) {
            throw new DaoException("sql error: " + ex);
        } finally {
            cerrarRecursos(rs, stat);
        }
    }

}
