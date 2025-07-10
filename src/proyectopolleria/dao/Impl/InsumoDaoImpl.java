package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.InsumoDao;
import proyectopolleria.model.Insumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsumoDaoImpl implements InsumoDao {

    private Connection conn;

    public InsumoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    final String insert = "INSERT INTO insumo(nombre, stock, stock_min, unidad,precio_unitario,proveedor_id) VALUES (?, ?, ?, ?, ?,?)";
    final String update = "UPDATE insumo SET stock = ?, stock_min = ?, precio_unitario = ? proveedor_id = ?";
    final String delete = "delete from insumo where id=?";
    final String selectAll = "select * from insumo";
    final String selectId = "select * from insumo where id=?";

    @Override
    public void crear(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, t.getNombre());
            stat.setDouble(2, t.getStock());
            stat.setString(3, t.getUnidad().name());
            stat.setInt(4, t.getIdProveedor());
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
    public void modificar(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, t.getNombre());
            stat.setDouble(2, t.getStock());
            stat.setDouble(3, t.getStockMin());
            stat.setString(4, t.getUnidad().name());
            stat.setDouble(5, t.getPrecioUnitario());
            stat.setInt(6, t.getIdProveedor());
            stat.setInt(7, t.getId());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("puede que no se actualizo xd");
            }

        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DaoException("Error cerrando el statement ?", e);
                }
            }

        }
    }

    @Override
    public void eliminar(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(delete);
            stat.setInt(1, t.getId());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("puede que no se elimino xd");
            }
        } catch (SQLException ex) {
            throw new DaoException("error en sql", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException(update, ex);
                }
            }
        }
    }

    private Insumo convertidor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        double strock = rs.getDouble("stock");
        double strockMin = rs.getDouble("stock_min");
        String unidadStr = rs.getString("unidad");
        double precioUnitario = rs.getDouble("precio_unitario");
        int idProveedor = rs.getInt("proveedor_id");
        Insumo.Unidad unidad = Insumo.Unidad.valueOf(unidadStr.toUpperCase());

        Insumo cliente = new Insumo(id, nombre, strock, strockMin, true, unidad, precioUnitario, idProveedor);
        cliente.setId(rs.getInt("id"));
        return cliente;
    }

    @Override
    public List<Insumo> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Insumo> Insumos = new ArrayList<>();
        try {
            stat = conn.prepareStatement(selectAll);
            rs = stat.executeQuery();
            while (rs.next()) {
                Insumos.add(convertidor(rs));
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
        return Insumos;
    }

    @Override
    public Insumo obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Insumo i = null;
        try {
            stat = conn.prepareStatement(selectId);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                i = convertidor(rs);
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
        return i;
    }
}
