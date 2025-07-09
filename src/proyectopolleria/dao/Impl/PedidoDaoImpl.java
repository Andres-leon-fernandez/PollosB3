package proyectopolleria.dao.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Pedido;
import proyectopolleria.dao.interfaces.PedidoDao;
import java.sql.Connection;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.dao.interfaces.OrdenDao;
import proyectopolleria.dao.interfaces.TrabajadorDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import proyectopolleria.model.Cliente;
import proyectopolleria.model.Orden;
import proyectopolleria.model.Trabajador;

public class PedidoDaoImpl implements PedidoDao {

    private Connection conn;
    private ClienteDao clienteDao;
    private TrabajadorDao trabajadorDao;
    private OrdenDao ordenDao;

    public PedidoDaoImpl(Connection conn, ClienteDao clienteDao, TrabajadorDao trabajadorDao, OrdenDao ordenDao) {
        this.conn = conn;
        this.clienteDao = clienteDao;
        this.trabajadorDao = trabajadorDao;
        this.ordenDao = ordenDao;
    }

    private static final String INSERT = "INSERT INTO pedidos(cliente_id, trabajador_id, fecha_hora, tipo, total) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pedidos SET cliente_id=?, trabajador_id=?, fecha_hora=?, tipo=?, total=? WHERE id=?";
    private static final String DELETE = "DELETE FROM pedidos WHERE id=?";
    private static final String SELECTALL = "SELECT id, cliente_id, trabajador_id, fecha_hora, tipo, total FROM pedidos";
    private static final String SELECTID = "SELECT id, cliente_id, trabajador_id, fecha_hora, tipo, total FROM pedidos WHERE id=?";

    @Override
    public void crear(Pedido t) throws DaoException {
        PreparedStatement stat = null;
        ResultSet generatedKeys = null;

        try {
            conn.setAutoCommit(false);  // Iniciar transacción
            stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            // Setear parámetros
            stat.setInt(1, t.getCliente().getId());

            if ("SALON".equals(t.getTipo())) {
                stat.setInt(2, t.getMOZO().getId());
            } else if ("DELIVERY".equals(t.getTipo())) {
                stat.setInt(2, t.getDELIVERY().getId());
            } else {
                throw new DaoException("Tipo de pedido inválido");
            }

            stat.setTimestamp(3, Timestamp.valueOf(t.getFechaHora()));
            stat.setString(4, t.getTipo());
            stat.setDouble(5, t.getTotal());

            int affectedRows = stat.executeUpdate();

            // Obtener ID generado
            if (affectedRows == 0) {
                throw new DaoException("Error al crear pedido, ninguna fila afectada");
            }

            generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next()) {
                t.setId(generatedKeys.getInt(1));
            } else {
                throw new DaoException("No se generó ID para el pedido");
            }

            // 2. Insertar las ORDENES relacionadas
            for (Orden orden : t.getOrdenes()) {
                orden.setPedido(t);  // Establecer relación bidireccional
                ordenDao.crear(orden);  // Insertar en base de datos
            }

            conn.commit();  // Confirmar transacción

        } catch (SQLException ex) {
            try {
                conn.rollback();  // Revertir en caso de error
            } catch (SQLException e) {
                throw new DaoException("Error al hacer rollback", e);
            }
            throw new DaoException("Error en base de datos", ex);
        } finally {
            // Cerrar recursos en orden inverso
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException ex) {
                throw new DaoException("Error al cerrar recursos", ex);
            }
        }
    }

    @Override
    public void modificar(Pedido t) throws DaoException {
        PreparedStatement stat = null;
        try {
            conn.setAutoCommit(false);
            stat = conn.prepareStatement(UPDATE);

            // Setear parámetros
            stat.setInt(1, t.getCliente().getId());

            if ("SALON".equals(t.getTipo())) {
                stat.setInt(2, t.getMOZO().getId());
            } else if ("DELIVERY".equals(t.getTipo())) {
                stat.setInt(2, t.getDELIVERY().getId());
            } else {
                throw new DaoException("Tipo de pedido inválido");
            }

            stat.setTimestamp(3, Timestamp.valueOf(t.getFechaHora()));
            stat.setString(4, t.getTipo());
            stat.setDouble(5, t.getTotal());
            stat.setInt(6, t.getId());

            int affectedRows = stat.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("No se actualizó ningún pedido");
            }

            // Actualizar órdenes
            ordenDao.eliminarPorPedido(t.getId());

            for (Orden orden : t.getOrdenes()) {
                orden.setPedido(t);
                ordenDao.crear(orden);
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new DaoException("Error en rollback", e);
            }
            throw new DaoException("Error al actualizar pedido", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("Error al cerrar recursos", ex);
                }
            }
        }
    }

    @Override
    public void eliminar(Pedido t) throws DaoException {
        PreparedStatement stat = null;
        try {
            conn.setAutoCommit(false);

            // Eliminar órdenes primero
            ordenDao.eliminarPorPedido(t.getId());

            // Luego eliminar pedido
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, t.getId());

            int affectedRows = stat.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("No se eliminó ningún pedido");
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new DaoException("Error en rollback", e);
            }
            throw new DaoException("Error al eliminar pedido", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DaoException("Error al cerrar recursos", ex);
                }
            }
        }

    }

    @Override
    public List<Pedido> listarTodos() throws DaoException {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement stat = conn.prepareStatement(SELECTALL); ResultSet rs = stat.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = mapearPedido(rs);
                pedidos.add(pedido);
            }
        } catch (SQLException | DaoException ex) {
            throw new DaoException("Error al listar pedidos", ex);
        }
        return pedidos;

    }

    @Override
    public Pedido obtener(Integer id) throws DaoException {
        try (PreparedStatement stat = conn.prepareStatement(SELECTID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }
        } catch (SQLException | DaoException ex) {
            throw new DaoException("Error al obtener pedido", ex);
        }
        return null;
    }

    @Override
    public List<Orden> listarPorPedido(Pedido pedido) throws DaoException {
        return ordenDao.listarPorPedido(pedido.getId());
    }

    @Override
    public void eliminarPorPedido(Integer pedidoId) throws DaoException {
        ordenDao.eliminarPorPedido(pedidoId);
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException, DaoException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));

        // Obtener cliente
        int clienteId = rs.getInt("cliente_id");
        Cliente cliente = clienteDao.obtener(clienteId);
        if (cliente == null) {
            throw new DaoException("Cliente no encontrado para ID: " + clienteId);
        }
        pedido.setCliente(cliente);

        // Obtener trabajador
        int trabajadorId = rs.getInt("trabajador_id");
        Trabajador trabajador = trabajadorDao.obtener(trabajadorId);
        if (trabajador == null) {
            throw new DaoException("Trabajador no encontrado para ID: " + trabajadorId);
        }

        String tipo = rs.getString("tipo");
        if ("SALON".equals(tipo)) {
            pedido.setMOZO(trabajador);
        } else if ("DELIVERY".equals(tipo)) {
            pedido.setDELIVERY(trabajador);
        }

        pedido.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        pedido.setTipo(tipo);
        pedido.setTotal(rs.getDouble("total"));

        // Obtener órdenes asociadas
        List<Orden> ordenes = ordenDao.listarPorPedido(pedido.getId());
        pedido.setOrdenes(ordenes);

        return pedido;
    }
}
