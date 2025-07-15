package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Insumo;

public interface InsumoService {

    void registrarInsumo(Insumo insumo) throws DaoException;

    void actualizarInsumo(Insumo insumo) throws DaoException;

    void eliminarInsumo(Insumo insumo) throws DaoException;

    Insumo obtenerPorId(int id) throws DaoException;

    List<Insumo> listarTodos() throws DaoException;
}
