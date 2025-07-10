package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.InsumoDao;
import proyectopolleria.model.Insumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsumoDaoImpl implements InsumoDao {

    private Connection conn;

    public InsumoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    final String insert = "INSERT INTO insumo(nombre, stock, stock_min, unidad,precio_unitario,proveedor_id) VALUES (?, ?, ?, ?, ?,?)";
    final String update = "UPDATE insumo SET stock = ?, stock_min = ?, precio_unitario = ? proveedor_id = ?";
    final String delete = "delete from insumo where id=?";
    final String selectAll = "select * from insumo";
    final String selectId = "select * from insumo where id=?";

    @Override
    public void crear(Insumo t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, t.getNombre());
            stat.setDouble(2, t.getStock());
            stat.setString(3, t.getUnidad().name());
            stat.setInt(4, t.getIdProveedor());
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
    public void modificar(Insumo t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Insumo t) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Insumo> listarTodos() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Insumo obtener(Integer id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
