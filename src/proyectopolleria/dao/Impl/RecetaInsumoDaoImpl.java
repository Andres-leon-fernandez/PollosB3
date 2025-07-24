package proyectopolleria.dao.Impl;

import proyectopolleria.dao.interfaces.RecetaInsumoDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.DTO.DetalleInsumoDTO;
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

    @Override
    public List<DetalleInsumoDTO> listarInsumosConProveedorPorProducto(int idProducto) throws DaoException {
        List<DetalleInsumoDTO> lista = new ArrayList<>();

        String sql = """
                SELECT 
                    i.id AS id_insumo,
                    i.nombre AS nombre_insumo,
                    ri.cantidad AS cantidad_usar,
                    p.ruc AS ruc_proveedor,
                    p.nombre AS nombre_proveedor
                FROM receta_insumo ri
                JOIN insumo i ON ri.insumo_id = i.id
                JOIN proveedor p ON i.proveedor_id = p.id
                JOIN receta r ON ri.receta_id = r.id
                JOIN producto pr ON r.producto_id = pr.id
                WHERE pr.id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleInsumoDTO dto = new DetalleInsumoDTO(
                        rs.getInt("id_insumo"),
                        rs.getString("nombre_insumo"),
                        rs.getDouble("cantidad_usar"),
                        rs.getString("ruc_proveedor"),
                        rs.getString("nombre_proveedor")
                );
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar detalle de insumos con proveedor", e);
        }

        return lista;
    }
}
