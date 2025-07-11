package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.model.Cliente;
import proyectopolleria.service.ClienteService;

public class AlmacenServiceImpl implements ClienteService {

    private ClienteDao clienteDao;

    public AlmacenServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public void registrarCliente(Cliente cliente) throws DaoException {
        clienteDao.crear(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws DaoException {
        clienteDao.modificar(cliente);
    }

    @Override
    public void eliminarCliente(Cliente cliente) throws DaoException {
        clienteDao.eliminar(cliente);
    }

    @Override
    public Cliente obtenerPorId(int id) throws DaoException {
        return clienteDao.obtener(id);
    }

    @Override
    public List<Cliente> listarTodos() throws DaoException {
        return clienteDao.listarTodos();
    }
}
