package proyectopolleria.dao.Impl;

import java.sql.*;
import proyectopolleria.dao.interfaces.*;
import proyectopolleria.util.Conexion;

public class DaoManagerImpl implements DaoManager {

    private Connection conn;
    

    private ClienteDao clienteDao = null;//ya esta
    private AlmacenMovimientoDao almacenMovimientoDao = null;
    private ComprobanteDao comprobanteDao = null;
    private DetalleRecetaDao detalleRecetaDao = null;
    private InsumoDao insumoDao = null;//ya esta
    private OrdenDao ordenDao = null;
    private PedidoDao pedidoDao = null;
    private ProductoDao productoDao = null;
    private ProveedorDao proveedorDao = null;
    private RecetaDao recetaDao = null;
    private TrabajadorDao trabajadorDao = null;

    public DaoManagerImpl(Connection conn) {
        this.conn = conn;
    }

    public void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada desde DaoManagerImpl.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

//    @Override
//    public AlmacenMovimientoDao getAlmacenMovimientoDao() {
//        if (almacenMovimientoDao == null) {
//            almacenMovimientoDao = new AlmacenMovimientoDaoImpl(conn);
//        }
//        return almacenMovimientoDao;
//    }

    @Override
    public ClienteDao getClienteDao() {
        if (clienteDao == null) {
            clienteDao = new ClienteDaoImpl(conn);
        }
        return clienteDao;
    }

//    @Override
//    public ComprobanteDao getcComprobanteDao() {
//        if (comprobanteDao == null) {
//            comprobanteDao = new ComprobanteDaoImpl(conn);
//        }
//        return comprobanteDao;
//    }

//    @Override
//    public DetalleRecetaDao getDetalleRecetaDao() {
//        if (detalleRecetaDao == null) {
//            detalleRecetaDao = new DetalleRecetaDaoImpl(conn);
//        }
//        return detalleRecetaDao;
//    }

    @Override
    public InsumoDao getInsumoDao() {
        if (insumoDao == null) {
            insumoDao = new InsumoDaoImpl(conn);
        }
        return insumoDao;
    }

//    @Override
//    public OrdenDao getOrdenDao() {
//        if (ordenDao == null) {
//            ordenDao = new OrdenDaoImpl(conn);
//        }
//        return ordenDao;
//    }
//
//    @Override
//    public PedidoDao getPedidoDao() {
//        if (pedidoDao == null) {
//            pedidoDao = new PedidoDaoImpl(conn);
//        }
//        return pedidoDao;
//    }

    @Override
    public ProductoDao getProductoDao() {
        if (productoDao == null) {
            productoDao = new ProductoDaoImpl(conn);
        }
        return productoDao;
    }

    @Override
    public ProveedorDao getProveedorDao() {
        if (proveedorDao == null) {
            proveedorDao = new ProveedorDaoImpl(conn);
        }
        return proveedorDao;
    }

//    @Override
//    public RecetaDao getRecetaDao() {
//        if (recetaDao == null) {
//            recetaDao = new RecetaDaoImpl(conn);
//        }
//        return recetaDao;
//    }

    @Override
    public TrabajadorDao getTrabajadorDao() {
        if (trabajadorDao == null) {
            trabajadorDao = new TrabajadorDaoImpl(conn);
        }
        return trabajadorDao;
    }
}
