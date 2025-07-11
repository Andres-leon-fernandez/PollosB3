/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.dao.Impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.RecetaDao;
import proyectopolleria.model.Receta;

public class RecetaDaoImpl implements RecetaDao {

    private final Connection conn;

    private final String INSERT = "INSERT INTO receta (producto_id) VALUES (?)";
    private final String SELECT_ALL = "SELECT * FROM receta";
    private final String SELECT_BY_ID = "SELECT * FROM receta WHERE id = ?";
    private final String DELETE = "DELETE FROM receta WHERE id = ?";
    private final String UPDATE = "UPDATE receta SET producto_id = ? WHERE id = ?";

    public RecetaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void crear(Receta r) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, r.getIdProducto());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se pudo insertar la receta");
            }

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado de la receta");
                }
            }

        } catch (SQLException ex) {
            throw new DaoException("Error al crear receta", ex);
        }
    }

    @Override
    public void modificar(Receta r) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(UPDATE)) {
            stat.setInt(1, r.getIdProducto());
            stat.setInt(2, r.getId());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se modificó ninguna receta");
            }

        } catch (SQLException ex) {
            throw new DaoException("Error al modificar receta", ex);
        }
    }

    @Override
    public void eliminar(Receta r) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(DELETE)) {
            stat.setInt(1, r.getId());

            if (stat.executeUpdate() == 0) {
                throw new DaoException("No se eliminó ninguna receta");
            }

        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar receta", ex);
        }
    }

    private Receta convertir(ResultSet rs) throws SQLException {
        Receta r = new Receta();
        r.setId(rs.getInt("id"));
        r.setIdProducto(rs.getInt("producto_id"));
        return r;
    }

    @Override
    public List<Receta> listarTodos() throws DaoException {
        List<Receta> recetas = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECT_ALL); ResultSet rs = stat.executeQuery()) {

            while (rs.next()) {
                recetas.add(convertir(rs));
            }

        } catch (SQLException ex) {
            throw new DaoException("Error al listar recetas", ex);
        }
        return recetas;
    }

    @Override
    public Receta obtener(Integer id) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);

            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return convertir(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException ex) {
            throw new DaoException("Error al obtener receta", ex);
        }
    }
}
