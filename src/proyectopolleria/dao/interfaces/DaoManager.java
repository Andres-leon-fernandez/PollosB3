package proyectopolleria.dao.interfaces;

public interface DaoManager {

    AlmacenMovimientoDao getAlmacenMovimientoDao();

    ClienteDao getClienteDao();

    ComprobanteDao getComprobanteDao();

    DetalleRecetaDao getDetalleRecetaDao();

    InsumoDao getInsumoDao();

    OrdenDao getOrdenDao();

    PedidoDao getPedidoDao();

    ProductoDao getProductoDao();

    ProveedorDao getProveedorDao();

    RecetaDao getRecetaDao();

    TrabajadorDao getTrabajadorDao();
}
