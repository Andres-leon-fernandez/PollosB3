package proyectopolleria.dao.Impl;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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

    public ComprobanteDaoImpl(Connection conn) {
        this.conn = conn;
    }

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
        // Fetch Pedido first
        int pedidoId = rs.getInt("pedido_id");
        Pedido pedido = 

        // Get other fields
        int id = rs.getInt("id");
        LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
        double total = rs.getDouble("total");
        String tipoComprobante = rs.getString("tipo_comprobante");
        String metodoPago = rs.getString("metodo_pago");

        return new Comprobante(id, pedido, fechaHora, total, tipoComprobante, metodoPago);
    }

    @Override
    public List<Comprobante> listarTodos() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Comprobante obtener(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
