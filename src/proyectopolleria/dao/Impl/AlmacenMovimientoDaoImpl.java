/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import java.sql.Connection;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.AlmacenMovimientoDao;
import proyectopolleria.model.AlmacenMovimiento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AlmacenMovimientoDaoImpl implements AlmacenMovimientoDao {

    private Connection conn;

    public AlmacenMovimientoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    private static final String INSERT = "INSERT INTO almacen_movimiento(id_insumo, tipo_movimiento, cantidad, fecha_hora) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE almacen_movimiento SET id_insumo = ?, tipo_movimiento = ?, cantidad = ?, fecha_hora = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM almacen_movimiento WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM almacen_movimiento";
    private static final String SELECT_ID = "SELECT * FROM almacen_movimiento WHERE id = ?";
    private static final String SELECT_BY_INSUMO = "SELECT * FROM almacen_movimiento WHERE id_insumo = ?";

    private AlmacenMovimiento convertir(ResultSet rs) throws SQLException {
        return new AlmacenMovimiento(
                rs.getInt("id"),
                rs.getInt("id_insumo"),
                AlmacenMovimiento.TipoMovimiento.valueOf(rs.getString("tipo_movimiento")),
                rs.getDouble("cantidad"),
                rs.getTimestamp("fecha_hora").toLocalDateTime()
        );
    }

    @Override
    public List<AlmacenMovimiento> obtenerMov(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void crear(AlmacenMovimiento t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(INSERT, stat.RETURN_GENERATED_KEYS);
            stat.setInt(1, t.getIdInsumo());
            stat.setString(2, t.getTipoMovimiento().name());
            stat.setDouble(3, t.getCantidad());
            stat.setTimestamp(4, Timestamp.valueOf(t.getFechaHora()));
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
    public void modificar(AlmacenMovimiento t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(AlmacenMovimiento t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AlmacenMovimiento> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<AlmacenMovimiento> mov = new ArrayList<>();
        try {
            stat = conn.prepareStatement(SELECT_ALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                mov.add(convertir(rs));
            }

        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }
            }
        }
        return mov;
    }

    @Override
    public AlmacenMovimiento obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        AlmacenMovimiento mov = null;
        try {
            stat = conn.prepareStatement(SELECT_ID);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                mov = convertir(rs);
            } else {
                throw new DaoException("no se ha encontrado ese registro");
            }

        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("error en sql", ex);
                }
            }
        }
        return mov;
    }

}
