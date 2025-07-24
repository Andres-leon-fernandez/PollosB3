package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.DTO.DetalleInsumoDTO;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.RecetaInsumo;

public interface RecetaInsumoService {

    List<RecetaInsumo> obtenerInsumosDeReceta(int idReceta) throws DaoException;

    void agregarInsumoAReceta(RecetaInsumo recetaInsumo) throws DaoException;

    public List<DetalleInsumoDTO> listarInsumosConProveedorPorProducto(int idProducto) throws DaoException;
}
