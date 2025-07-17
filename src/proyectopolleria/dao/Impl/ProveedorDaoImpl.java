package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.ProveedorDao;
import java.sql.Connection;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Proveedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProveedorDaoImpl implements ProveedorDao {

    private Connection conn;

    public ProveedorDaoImpl(Connection conn) {
        this.conn = conn;
    }

    final String insert = "INSERT INTO proveedor(nombre, ruc, telefono, direccion,correo) VALUES (?, ?, ?, ?, ?)";
    final String update = "UPDATE proveedores SET nombre=?, telefono=?, direccion=?, correo=? WHERE ruc=?";
    final String delete = "delete from proveedor where id=?";
    final String selectAll = "select * from proveedor";
    final String selectId = "select * from proveedor where id=?";

    @Override
    public void crear(Proveedor t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert, stat.RETURN_GENERATED_KEYS);
            stat.setString(1, t.getNombre());
            stat.setString(2, t.getRuc());
            stat.setString(3, t.getTelefono());
            stat.setString(4, t.getDireccion());
            stat.setString(5, t.getCorreo());
            int affectedRows = stat.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("No se pudo guardar el proveedor");
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
    public void modificar(Proveedor t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, t.getNombre());
            stat.setString(2, t.getTelefono());
            stat.setString(3, t.getDireccion());
            stat.setString(4, t.getCorreo());
            stat.setString(5, t.getRuc());
            int affected = stat.executeUpdate();
            if (affected == 0) {
                throw new DaoException("No se encontró proveedor con RUC: " + t.getRuc());
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
    public void eliminar(Proveedor t) throws DaoException {
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

    private Proveedor convertidor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String ruc = rs.getString("ruc");
        String telefono = rs.getString("telefono");
        String direccion = rs.getString("direccion");
        String correo = rs.getString("correo");

        Proveedor proveedor = new Proveedor(id, nombre, ruc, telefono, direccion, correo);
        proveedor.setId(rs.getInt("id"));
        return proveedor;
    }

    @Override
    public List<Proveedor> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Proveedor> Proveedores = new ArrayList<>();
        try {
            stat = conn.prepareStatement(selectAll);
            rs = stat.executeQuery();
            while (rs.next()) {
                Proveedores.add(convertidor(rs));
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
        return Proveedores;
    }

    @Override
    public Proveedor obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Proveedor p = null;
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
