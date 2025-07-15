package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.RecetaDao;
import proyectopolleria.model.Receta;
import proyectopolleria.service.interfaz.RecetaService;

public class RecetaServiceImpl implements RecetaService {

    private RecetaDao dao;

    public RecetaServiceImpl(RecetaDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarReceta(Receta receta) throws DaoException {
        dao.crear(receta);
    }

    @Override
    public void actualizarReceta(Receta receta) throws DaoException {
        dao.modificar(receta);
    }

    @Override
    public void eliminarReceta(Receta idReceta) throws DaoException {
        dao.eliminar(idReceta);
    }

    @Override
    public Receta obtenerRecetaPorId(Integer idReceta) throws DaoException {
        return dao.obtener(idReceta);
    }

    @Override
    public List<Receta> listarRecetas() throws DaoException {
        return dao.listarTodos();
    }

}
