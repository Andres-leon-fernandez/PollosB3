package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.model.Cliente;
import proyectopolleria.service.ValidationException;
import proyectopolleria.service.interfaz.ClienteService;

public class ClienteServiceImpl implements ClienteService {

    private ClienteDao clienteDao;

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    private void ValidarCliente(Cliente c, boolean isNew) throws ValidationException {
        if (c == null) {
            throw new ValidationException("Cliente nulo");
        }
        if (c.getDni() == null || c.getDni().trim().isEmpty()) {
            throw new ValidationException("Dni nulo");
        }
        if (c.getDni().trim().length() != 8 && c.getDni().trim().length() != 11) {
            throw new ValidationException("Dni invalido");
        }
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) {
            throw new ValidationException("Nombre vacio");
        }
        if (c.getTelefono() != null && !c.getTelefono().matches("^9[0-9]{8}$")) {
            throw new ValidationException("Telefono nulo o con valores incorrectos");
        }
        if (isNew || (c.getId() != null && !c.DNI_NO_CAMBIO_EN_ESTA_ACTUALIZACION(clienteDao.obtener(c.getId()).getDni(), c.getDni()))) {
            try {
                // Asumiendo que ClienteDao tiene un método para verificar la existencia del DNI
                // Necesitarás añadir este método a tu interfaz ClienteDao y su implementación.
                Cliente existingCliente = clienteDao.obtenerPorDni(c.getDni());
                // Si existe un cliente con ese DNI Y no es el mismo cliente que estamos actualizando, entonces es un DNI duplicado.
                if (existingCliente != null && (isNew || !existingCliente.getId().equals(c.getId()))) {
                    throw new ValidationException("Ya existe un cliente con el DNI proporcionado.");
                }
            } catch (DaoException e) {
                // Si hay un error de DAO durante la validación, envuélvelo o relánzalo
                throw new DaoException("Error al verificar la unicidad del DNI: " + e.getMessage(), e);
            }
        }
    }

    private boolean DNI_NO_CAMBIO_EN_ESTA_ACTUALIZACION(String oldDni, String newDni) {
        return oldDni != null && oldDni.equals(newDni);
    }

    @Override
    public void registrarCliente(Cliente cliente) throws DaoException {
        ValidarCliente(cliente, true);
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
