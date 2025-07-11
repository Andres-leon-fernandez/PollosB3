package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.ProductoDao;

import java.sql.Connection;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDaoImpl implements ProductoDao {

    final String insert = "INSERT INTO producto(nombre, ruc, telefono, direccion,correo) VALUES (?, ?, ?, ?, ?)";
    final String update = "UPDATE producto SET telefono = ?, direccion = ?, correo = ? WHERE id = ?";
    final String delete = "delete from producto where id=?";
    final String selectAll = "select * from producto";
    final String selectId = "select * from producto where id=?";

    private Connection conn;

    public ProductoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void crear(Producto t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert, stat.RETURN_GENERATED_KEYS);
            stat.setString(1, t.getDescripcion());
            stat.setDouble(2, t.getPrecio());
            stat.setString(3, t.getCategoria());
            stat.setBoolean(4, t.isActivo());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("Pueed que no se guardo xd");
            }
            rs = stat.executeQuery();
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
    public void modificar(Producto t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, t.getDescripcion());
            stat.setDouble(2, t.getPrecio());
            stat.setString(3, t.getCategoria());
            stat.setBoolean(4, t.isActivo());
            stat.setInt(5, t.getId());
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
    public void eliminar(Producto t) throws DaoException {
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
                    throw new DaoException("error en sql", ex);
                }
            }
        }
    }

    private Producto convertidor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String descripcion = rs.getString("descripcion");
        double ruc = rs.getDouble("precio");
        String telefono = rs.getString("categoria");
        boolean activo = rs.getBoolean("activo");

        Producto producto = new Producto(id, descripcion, ruc, telefono, activo);
        producto.setId(rs.getInt("id"));
        return producto;
    }

    @Override
    public List<Producto> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Producto> Productos = new ArrayList<>();
        try {
            stat = conn.prepareStatement(selectAll);
            rs = stat.executeQuery();
            while (rs.next()) {
                Productos.add(convertidor(rs));
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
        return Productos;
    }

    @Override
    public Producto obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Producto p = null;
        try {
            stat = conn.prepareStatement(selectId);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                p = convertidor(rs);
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
        return p;
    }

}
