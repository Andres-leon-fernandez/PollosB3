package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.AlmacenMovimientoDao;
import proyectopolleria.model.AlmacenMovimiento;
import proyectopolleria.service.interfaz.AlmacenService;

public class AlmacenServiceImpl implements AlmacenService {

    private AlmacenMovimientoDao dao;

    public AlmacenServiceImpl(AlmacenMovimientoDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarCliente(AlmacenMovimiento aMov) throws DaoException {
        dao.crear(aMov);
    }

    @Override
    public void actualizarCliente(AlmacenMovimiento aMov) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarCliente(AlmacenMovimiento aMov) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AlmacenMovimiento obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<AlmacenMovimiento> listarTodos() throws DaoException {
        return dao.listarTodos();
    }

}
