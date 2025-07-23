package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.RecetaInsumoDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Insumo;
import proyectopolleria.model.RecetaInsumo;

public class RecetaInsumoDaoImpl implements RecetaInsumoDAO {

    private final Connection conn;

    public RecetaInsumoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertarInsumoEnReceta(RecetaInsumo recetaInsumo) throws DaoException {
        String sql = "INSERT INTO receta_insumo (receta_id, insumo_id, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recetaInsumo.getIdReceta());
            stmt.setInt(2, recetaInsumo.getIdInsumo());
            stmt.setDouble(3, recetaInsumo.getCantidad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error al insertar insumo en receta", e);
        }
    }

    @Override
    public List<RecetaInsumo> obtenerInsumosDeReceta(int idReceta) throws DaoException {
        List<RecetaInsumo> lista = new ArrayList<>();
        String sql = """
        SELECT insumo_id, receta_id, cantidad
        FROM receta_insumo
        WHERE receta_id = ?
    """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReceta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RecetaInsumo ri = new RecetaInsumo();
                ri.setIdInsumo(rs.getInt("insumo_id"));
                ri.setIdReceta(rs.getInt("receta_id"));
                ri.setCantidad(rs.getDouble("cantidad"));
                lista.add(ri);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener insumos de receta", e);
        }
        return lista;
    }
}
