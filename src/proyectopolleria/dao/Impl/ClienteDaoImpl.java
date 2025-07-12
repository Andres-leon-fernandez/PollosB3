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
    private final String selectDni;

    public ClienteDaoImpl(Connection conn, String selectDni) {
        this.conn = conn;
        this.selectDni = "select * from cliente where dni=?";
    }

    private static final String INSERT = "INSERT INTO cliente(dni, nombre, telefono, direccion, referencia) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cliente SET telefono = ?, direccion = ?, referencia = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM cliente WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM cliente";
    private static final String SELECT_ID = "SELECT * FROM cliente WHERE id = ?";
    private static final String SELECT_DNI = "SELECT * FROM cliente WHERE dni = ?";

    @Override
    public void crear(Cliente t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(INSERT, stat.RETURN_GENERATED_KEYS);
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
            stat = conn.prepareStatement(UPDATE);
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
            stat = conn.prepareStatement(DELETE);
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

    private Cliente convertidor(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setReferencia(rs.getString("referencia"));
        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            stat = conn.prepareStatement(SELECT_ALL);
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
            stat = conn.prepareStatement(SELECT_ID);
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

    @Override
    public Cliente obtenerPorDni(String dni) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente cli = null;
        try {
            stat = conn.prepareStatement(SELECT_DNI);
            stat.setString(1, dni);
            rs = stat.executeQuery();
            if (rs.next()) {
                cli = convertidor(rs);
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
        return cli;
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
