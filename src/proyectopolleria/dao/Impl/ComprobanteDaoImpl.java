package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.ComprobanteDao;

import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Comprobante;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComprobanteDaoImpl implements ComprobanteDao {

    private Connection conn;

    private final String INSERT = "INSERT INTO comprobante(pedido_id, fecha_hora, tipo_comprobante, metodo_pago) VALUES (?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM comprobante";
    private final String SELECT_BY_PEDIDO = "SELECT * FROM comprobante WHERE pedido_id = ?";

    public ComprobanteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void crear(Comprobante t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(INSERT, stat.RETURN_GENERATED_KEYS);
            stat.setInt(1, t.getIdPedido());
            stat.setTimestamp(2, Timestamp.valueOf(t.getFechaHora()));
            stat.setString(3, t.getTipoComprobante().name());
            stat.setString(4, t.getMetodoPago().name());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("Pueed que no se guardo xd");
            }
            rs = stat.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            } else {
                throw new DaoException("error xd");
            }
        } catch (SQLException ex) {
            throw new DaoException("error xd", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DaoException("error xd", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en bd", ex);
                }
            }
        }
    }

    @Override
    public void modificar(Comprobante t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Comprobante t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Comprobante convertidor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idPedido = rs.getInt("pedido_id");
        LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
        Comprobante.TipoComprobante tipoComprobante = Comprobante.TipoComprobante.valueOf(rs.getString("tipo_comprobante"));
        Comprobante.MetodoPago metodoPago = Comprobante.MetodoPago.valueOf(rs.getString("metodo_pago"));

        Comprobante c = new Comprobante(id, idPedido, fechaHora, 0.0, tipoComprobante, metodoPago); // total se ignora si no está en BD
        return c;

    }

    @Override
    public List<Comprobante> listarTodos() throws DaoException {
        List<Comprobante> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_ALL); ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                lista.add(convertidor(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al listar comprobantes", ex);
        }
        return lista;
    }

    @Override
    public Comprobante obtener(Integer id) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM comprobante WHERE id = ?")) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return convertidor(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al obtener comprobante por ID", ex);
        }
    }

}
