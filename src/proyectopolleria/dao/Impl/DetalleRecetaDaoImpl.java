package proyectopolleria.dao.Impl;

import java.sql.Connection;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.DetalleRecetaDao;
import proyectopolleria.model.DetalleReceta;
import java.sql.*;
import java.util.ArrayList;

public class DetalleRecetaDaoImpl implements DetalleRecetaDao {

    private Connection conn;

    public DetalleRecetaDaoImpl(Connection conn) {
        this.conn = conn;
    }
    private final String INSERT = "INSERT INTO receta_insumo (receta_id, insumo_id, cantidad) VALUES (?, ?, ?)";
    private final String DELETE_POR_RECETA = "DELETE FROM receta_insumo WHERE receta_id = ?";
    private final String SELECT_POR_RECETA = "SELECT * FROM receta_insumo WHERE receta_id = ?";

    @Override
    public void crear(DetalleReceta t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(INSERT)) {
            stat.setInt(1, t.getIdReceta());
            stat.setInt(2, t.getIdInsumo());
            stat.setDouble(3, t.getCantidad());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se insertó ningún detalle de receta");
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al insertar detalle de receta", ex);
        }
    }

    @Override
    public void modificar(DetalleReceta t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(DetalleReceta t) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(DELETE_POR_RECETA)) {
            stat.setInt(1, t.getIdReceta());
            stat.executeUpdate(); // Se puede eliminar 0 o más
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar detalles de receta", ex);
        }
    }

    @Override
    public List<DetalleReceta> listarTodos() throws DaoException {
        List<DetalleReceta> detalles = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_POR_RECETA)) {
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                DetalleReceta d = new DetalleReceta();
                d.setIdReceta(rs.getInt("receta_id"));
                d.setIdInsumo(rs.getInt("insumo_id"));
                d.setCantidad(rs.getDouble("cantidad"));
                detalles.add(d);
            }
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Error al listar detalles de receta", ex);
        }
        return detalles;
    }

    @Override
    public DetalleReceta obtener(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<DetalleReceta> listarPorReceta(int idReceta) throws DaoException {
        List<DetalleReceta> detalles = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_POR_RECETA)) {
            stat.setInt(1, idReceta);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                DetalleReceta d = new DetalleReceta();
                d.setIdReceta(rs.getInt("receta_id"));
                d.setIdInsumo(rs.getInt("insumo_id"));
                d.setCantidad(rs.getDouble("cantidad"));
                detalles.add(d);
            }
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Error al listar detalles por receta", ex);
        }
        return detalles;
    }

}
