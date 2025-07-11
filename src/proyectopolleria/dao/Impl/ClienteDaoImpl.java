package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import proyectopolleria.util.Conexion;

public class ClienteDaoImpl implements ClienteDao {

    private Connection conn;

    public ClienteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    final String insert = "INSERT INTO cliente(dni, nombre, telefono, direccion, referencia) VALUES (?, ?, ?, ?, ?)";
    final String update = "UPDATE cliente SET telefono = ?, direccion = ?, referencia = ? WHERE id = ?";
    final String delete = "delete from cliente where id=?";
    final String selectAll = "select * from cliente";
    final String selectId = "select * from cliente where id=?";

    @Override
    public void crear(Cliente t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert, stat.RETURN_GENERATED_KEYS);
            stat.setString(1, t.getDni());
            stat.setString(2, t.getNombre());
            stat.setString(3, t.getTelefono());
            stat.setString(4, t.getDireccion());
            stat.setString(5, t.getReferencia());
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
    public void modificar(Cliente t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, t.getTelefono());
            stat.setString(2, t.getDireccion());
            stat.setString(3, t.getReferencia());
            stat.setInt(4, t.getId());
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
    public void eliminar(Cliente t) throws DaoException {
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
            stat = conn.prepareStatement(selectAll);
            rs = stat.executeQuery();
            while (rs.next()) {
                clientes.add(convertidor(rs));
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
        return clientes;
    }

    @Override
    public Cliente obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente c = null;
        try {
            stat = conn.prepareStatement(selectId);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                c = convertidor(rs);
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
        return c;
    }

//public static void main(String[] args) {
//try {
//Connection conn = Conexion.getInstancia().getConexion();
//DaoManagerImpl manager = new DaoManagerImpl(conn);
//ClienteDao clienteDao = manager.getClienteDao();
//
//// Crear nuevo cliente
//Cliente nuevo = new Cliente("12342678", "Juan Pérez", "987654321", "Av. Siempre Viva 123", "Frente al parque");
//clienteDao.crear(nuevo);
//System.out.println("Cliente creado con ID: " + nuevo.getId());
//
//// Listar todos
//List<Cliente> lista = clienteDao.listarTodos();
//System.out.println("Listado de clientes:");
//for (Cliente c : lista) {
//System.out.println(c.getId() + " - " + c.getNombre() + " - " + c.getDni());
//}
//
//// Obtener por ID
//Cliente buscado = clienteDao.obtener(nuevo.getId());
//System.out.println("Cliente encontrado: " + buscado.getNombre());
//
//// Modificar
//buscado.setTelefono("999888777");
//clienteDao.modificar(buscado);
//System.out.println("Cliente modificado");
//
//// Eliminar
////            clienteDao.eliminar(buscado);
////            System.out.println("Cliente eliminado");
//
//
//// Cerrar conexión
//manager.cerrarConexion();
//
//} catch (DaoException e) {
//System.err.println("Error DAO: " + e.getMessage());
//e.printStackTrace();
//}
//}
}
