package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.PedidoDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Pedido;

public class PedidoDaoImpl implements PedidoDao {

    private Connection conn;

    public PedidoDaoImpl(Connection conn) {
        this.conn = conn;
    }
    private final String INSERT = "INSERT INTO pedido (cliente_id, trabajador_mozo_id, trabajador_delivery_id, fecha_hora, tipo, total) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM pedido";
    private final String SELECT_BY_ID = "SELECT * FROM pedido WHERE id = ?";
    private final String DELETE = "DELETE FROM pedido WHERE id = ?";

    @Override
    public void crear(Pedido p) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, p.getIdCliente());
            if (p.getIdMOZO() != null) {
                stat.setInt(2, p.getIdMOZO());
            } else {
                stat.setNull(2, Types.INTEGER);
            }
            if (p.getIdDELIVERY() != null) {
                stat.setInt(3, p.getIdDELIVERY());
            } else {
                stat.setNull(3, Types.INTEGER);
            }
            stat.setTimestamp(4, Timestamp.valueOf(p.getFechaHora()));
            stat.setString(5, p.getTipo().name());
            stat.setDouble(6, p.getTotal());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se insertó el pedido");
            }

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al insertar pedido", e);
        }
    }

    @Override
    public void modificar(Pedido t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Pedido t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(DELETE)) {
            stat.setInt(1, t.getId());
            stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar pedido", ex);
        }
    }

    private Pedido convertir(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setIdCliente(rs.getInt("cliente_id"));
        p.setIdMOZO(rs.getObject("trabajador_mozo_id") != null ? rs.getInt("trabajador_mozo_id") : null);
        p.setIdDELIVERY(rs.getObject("trabajador_delivery_id") != null ? rs.getInt("trabajador_delivery_id") : null);
        p.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        p.setTipo(Pedido.tipoPedido.valueOf(rs.getString("tipo")));
        p.setTotal(rs.getDouble("total"));
        return p;
    }

    @Override
    public List<Pedido> listarTodos() throws DaoException {
        List<Pedido> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_ALL); ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                lista.add(convertir(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar pedidos", e);
        }
        return lista;
    }

    @Override
    public Pedido obtener(Integer id) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return convertir(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener pedido", e);
        }
        return null;
    }

}
