package proyectopolleria.dao.Impl;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.model.Cliente;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDaoImpl implements ClienteDao {

    private Connection conn;

    public ClienteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    static final String INSERT = "INSERT INTO receta(dni, nombre, telefono,direccion,referencia) VALUES (?, ?, ?,?,?)";
    static final String UPDATE = "UPDATE receta SET dni = ?, nombre = ?, telefono = ?,direccion=? ,referencia=? WHERE id = ?";
    static final String DELETE = "DELETE FROM receta WHERE id = ?";
    static final String SELECTID = "SELECT * FROM receta WHERE id = ?";
    static final String SELECTALL = "SELECT * FROM receta";

    @Override
    public void crear(Cliente t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, t.getDni());
            stat.setString(2, t.getNombre());
            stat.setString(3, t.getTelefono());
            stat.setString(4, t.getDireccion());
            stat.setString(5, t.getReferencia());

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
    public void modificar(Cliente t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, t.getDni());
            stat.setString(2, t.getNombre());
            stat.setString(3, t.getTelefono());
            stat.setString(4, t.getDireccion());
            stat.setString(5, t.getReferencia());
            stat.setInt(6, t.getId());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("error al modificar");
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

    @Override
    public void eliminar(Cliente t) throws DaoException {
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

    private Cliente convertidor(ResultSet rs) throws SQLException {
        String dni = rs.getString("dni");
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");
        String direccion = rs.getString("direccion");
        String referencia = rs.getString("referencia");
        Cliente cliente = new Cliente(dni, nombre, telefono, direccion, referencia);
        cliente.setId(rs.getInt("id"));
        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            stat = conn.prepareStatement(SELECTALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                clientes.add(convertidor(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException("error en sql ", ex);
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
        return clientes;
    }

    @Override
    public Cliente obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            stat = conn.prepareStatement(SELECTID);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                cliente = convertidor(rs);
            } else {
                throw new DaoException("error en sql");
            }
        } catch (SQLException ex) {
            throw new DaoException("error en sql ", ex);
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
        return cliente;
    }

}
