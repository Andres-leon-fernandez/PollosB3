package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.InsumoDao;
import proyectopolleria.model.Insumo;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import proyectopolleria.dao.interfaces.ProveedorDao;
import proyectopolleria.model.Insumo.Unidad;

public class InsumoDaoImpl implements InsumoDao {

    private Connection conn;
    private ProveedorDao proveedordao;

    static final String INSERT = "INSERT INTO receta(dni, nombre, telefono,direccion,referencia) VALUES (?, ?, ?,?,?)";
    static final String UPDATE = "UPDATE receta SET dni = ?, nombre = ?, telefono = ?,direccion=? ,referencia=? WHERE id = ?";
    static final String DELETE = "DELETE FROM receta WHERE id = ?";
    static final String SELECTID = "SELECT * FROM receta WHERE id = ?";
    static final String SELECTALL = "SELECT * FROM receta";

    @Override
    public void crear(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, t.getNombre());
            stat.setDouble(2, t.getStock());
            stat.setDouble(3, t.getStockMin());
            stat.setBoolean(4, t.isDisponible());
            stat.setString(5, t.getUnidad().name());
            stat.setDouble(6, t.getPrecioUnitario());
            stat.setInt(7, t.getProveedor().getId());
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
    public void modificar(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, t.getNombre());
            stat.setDouble(2, t.getStock());
            stat.setDouble(3, t.getStockMin());
            stat.setBoolean(4, t.isDisponible());
            stat.setString(5, t.getUnidad().name());
            stat.setDouble(6, t.getPrecioUnitario());
            stat.setInt(7, t.getProveedor().getId());
            stat.setInt(8, t.getId());
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
    public void eliminar(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, t.getId());
            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se eliminó ningún insumo");
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar insumo", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("Error cerrando conexión", ex);
                }
            }
        }
    }

    private Insumo convertidor(ResultSet rs) throws SQLException, DaoException {
        Insumo insumo = new Insumo();

        // Campos básicos
        insumo.setId(rs.getInt("id"));
        insumo.setNombre(rs.getString("nombre"));
        insumo.setStock(rs.getDouble("stock"));
        insumo.setStockMin(rs.getDouble("stock_min"));
        insumo.setDisponible(rs.getBoolean("disponible"));
        insumo.setPrecioUnitario(rs.getDouble("precio_unitario"));

        // Conversión segura del string a enum Unidad (con toUpperCase por si acaso)
        String unidadString = rs.getString("unidad");
        if (unidadString != null) {
            try {
                insumo.setUnidad(Unidad.valueOf(unidadString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new DaoException("Valor de unidad inválido en BD: " + unidadString, e);
            }
        }

        // Cargar proveedor solo si proveedor_id no es NULL
        int proveedorId = rs.getInt("proveedor_id");
        if (!rs.wasNull()) {
            insumo.setProveedor(proveedordao.obtener(proveedorId));
        } else {
            insumo.setProveedor(null); // o lanzar una excepción si el proveedor es obligatorio
        }

        return insumo;
    }

    private void cerrarRecursos(ResultSet rs, PreparedStatement stat) throws DaoException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException ex) {
            throw new DaoException("Error cerrando recursos", ex);
        }
    }

    @Override
    public List<Insumo> listarTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Insumo> lista = new ArrayList<>();
        try {
            stat = conn.prepareStatement(SELECTALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                lista.add(convertidor(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al listar insumos", ex);
        } finally {
            cerrarRecursos(rs, stat);
        }
        return lista;
    }

    @Override
    public Insumo obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(SELECTID);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                return convertidor(rs);
            } else {
                throw new DaoException("No se encontró insumo con ID " + id);
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al obtener insumo", ex);
        } finally {
            cerrarRecursos(rs, stat);
        }
    }

}
