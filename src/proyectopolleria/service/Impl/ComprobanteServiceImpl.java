package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ComprobanteDao;
import proyectopolleria.model.Comprobante;
import proyectopolleria.service.interfaz.ComprobanteService;

public class ComprobanteServiceImpl implements ComprobanteService {

    private ComprobanteDao dao;

    public ComprobanteServiceImpl(ComprobanteDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarCliente(Comprobante comprobante) throws DaoException {
        dao.crear(comprobante);
    }

    @Override
    public Comprobante obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Comprobante> listarTodos() throws DaoException {
        return dao.listarTodos();
    }

}
