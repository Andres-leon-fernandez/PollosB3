package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.AlmacenMovimientoDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.AlmacenMovimiento;

public class AlmacenMovimientoImpl implements AlmacenMovimientoDao {

    private Connection conn;

    private final String INSERT = "INSERT INTO almacen_movimiento(insumo_id, tipo_movimiento, cantidad, fecha_hora) VALUES (?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM almacen_movimiento ORDER BY fecha_hora DESC";
    private final String SELECT_BY_INSUMO = "SELECT * FROM almacen_movimiento WHERE insumo_id = ? ORDER BY fecha_hora DESC";

    public AlmacenMovimientoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void crear(AlmacenMovimiento t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, t.getIdInsumo());
            stat.setString(2, t.getTipoMovimiento().name());
            stat.setDouble(3, t.getCantidad());
            stat.setTimestamp(4, Timestamp.valueOf(t.getFechaHora()));

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se registró el movimiento");
            }

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al registrar movimiento", e);
        }
    }

    private AlmacenMovimiento convertir(ResultSet rs) throws SQLException {
        AlmacenMovimiento mov = new AlmacenMovimiento();
        mov.setId(rs.getInt("id"));
        mov.setIdInsumo(rs.getInt("insumo_id"));
        mov.setTipoMovimiento(AlmacenMovimiento.TipoMovimiento.valueOf(rs.getString("tipo_movimiento")));
        mov.setCantidad(rs.getDouble("cantidad"));
        mov.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        return mov;
    }

    @Override
    public void modificar(AlmacenMovimiento t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(AlmacenMovimiento t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AlmacenMovimiento> listarTodos() throws DaoException {
        List<AlmacenMovimiento> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_ALL); ResultSet rs = stat.executeQuery()) {

            while (rs.next()) {
                lista.add(convertir(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al listar movimientos", e);
        }
        return lista;
    }

    @Override
    public AlmacenMovimiento obtener(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AlmacenMovimiento> obtenerMov(Integer id) throws DaoException {
        List<AlmacenMovimiento> lista = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_INSUMO)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertir(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar movimientos por insumo", e);
        }
        return lista;
    }
}
