/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.TrabajadorDao;

import java.sql.Connection;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Trabajador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectopolleria.util.Conexion;
import proyectopolleria.util.Sha256;

public class TrabajadorDaoImpl implements TrabajadorDao {

    private Connection conn;

    public TrabajadorDaoImpl(Connection conn) {
        this.conn = conn;
    }

    final String insert = "INSERT INTO trabajador(password, dni, nombre, correo,telefono,tipo,usuario) VALUES (?, ?, ?, ?, ?,?,?)";
    final String update = "UPDATE trabajador SET password = ?, correo = ?, telefono = ?, tipo = ? WHERE id = ?";
    final String delete = "delete from trabajador where id=?";
    final String selectAll = "select * from trabajador";
    final String selectId = "select * from trabajador where id=?";

    @Override
    public void crear(Trabajador t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert, stat.RETURN_GENERATED_KEYS);
            stat.setString(1, Sha256.sha256(t.getPassword()));
            stat.setString(2, t.getDni());
            stat.setString(3, t.getNombre());
            stat.setString(4, t.getCorreo());
            stat.setString(5, t.getTelefono());
            stat.setString(6, t.getTipoTrabajador().name());
            stat.setString(7, t.getUser());
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
    public void modificar(Trabajador t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, t.getPassword());
            stat.setString(2, t.getCorreo());
            stat.setString(3, t.getTelefono());
            stat.setString(4, t.getTipoTrabajador().name());
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
    public void eliminar(Trabajador t) throws DaoException {
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

    private Trabajador convertidor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String user = rs.getString("usuario");
        String dni = rs.getString("dni");
        String nombre = rs.getString("nombre");
        String correo = rs.getString("correo");
        String telefono = rs.getString("telefono");
        boolean activo = rs.getBoolean("activo");
        String tipo = rs.getString("tipo");
        boolean disponible = rs.getBoolean("disponible");

        Trabajador.TipoTrabajador tipoT = Trabajador.TipoTrabajador.valueOf(tipo.toString());

        Trabajador trabajador = new Trabajador(id, user, dni, nombre, correo, telefono, activo, tipoT, disponible);
        trabajador.setId(rs.getInt("id"));
        return trabajador;
    }

    @Override
    public List<Trabajador> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Trabajador> Trabajadores = new ArrayList<>();
        try {
            stat = conn.prepareStatement(selectAll);
            rs = stat.executeQuery();
            while (rs.next()) {
                Trabajadores.add(convertidor(rs));
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
        return Trabajadores;
    }

    @Override
    public Trabajador obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Trabajador t = null;
        try {
            stat = conn.prepareStatement(selectId);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                t = convertidor(rs);
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
        return t;
    }

}
