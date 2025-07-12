
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.InsumoDao;
import proyectopolleria.model.Insumo;
import proyectopolleria.service.interfaz.InsumoService;

public class InsumoServiceImpl implements InsumoService{
    private InsumoDao dao;

    public InsumoServiceImpl(InsumoDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarInsumo(Insumo insumo) throws DaoException {
        dao.crear(insumo);
    }

    @Override
    public void actualizarInsumo(Insumo insumo) throws DaoException {
        dao.modificar(insumo);
    }

    @Override
    public void eliminarInsumo(Insumo insumo) throws DaoException {
        dao.eliminar(insumo);
    }

    @Override
    public Insumo obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Insumo> listarTodos() throws DaoException {
        return dao.listarTodos();
    }
    
}
