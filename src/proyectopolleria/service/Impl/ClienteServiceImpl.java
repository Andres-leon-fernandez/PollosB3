package proyectopolleria.service.Impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.ClienteDao;
import proyectopolleria.model.Cliente;
import proyectopolleria.service.ValidationException;
import proyectopolleria.service.interfaz.ClienteService;

public class ClienteServiceImpl implements ClienteService {

    private ClienteDao clienteDao;
    private static final Logger LOGGER = Logger.getLogger(ClienteServiceImpl.class.getName());

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    private void ValidarCliente(Cliente c, boolean isNew) throws ValidationException, DaoException {
        if (c == null) {
            throw new ValidationException("El cliente no puede ser nulo.");
        }
        if (c.getDni() == null || c.getDni().trim().isEmpty()) {
            throw new ValidationException("El DNI no puede ser nulo o vacío.");
        }
        if (c.getDni().trim().length() != 8 && c.getDni().trim().length() != 11) {
            throw new ValidationException("El DNI debe tener 8 (DNI) u 11 (RUC) caracteres.");
        }
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre no puede ser nulo o vacío.");
        }
        if (c.getTelefono() != null && !c.getTelefono().matches("^9[0-9]{8}$")) {
            throw new ValidationException("El teléfono es inválido. Debe comenzar con '9' y tener 9 dígitos.");
        }
        Cliente existingCliente = clienteDao.obtenerPorDni(c.getDni());
        if (isNew) {
            // En caso de un nuevo registro, el DNI no debe existir.
            if (existingCliente != null) {
                throw new ValidationException("Ya existe un cliente con el DNI proporcionado.");
            }
        } else {
            // En caso de una actualización, el DNI solo debe ser único si ha cambiado.
            if (existingCliente != null && !existingCliente.getId().equals(c.getId())) {
                throw new ValidationException("Ya existe un cliente con el DNI proporcionado.");
            }
        }
    }

    @Override
    public void registrarCliente(Cliente cliente) throws DaoException {
        try {
            ValidarCliente(cliente, false);
            clienteDao.crear(cliente);
        } catch (ValidationException ex) {
            try {
                throw ex;
            } catch (ValidationException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        } catch (DaoException ex) {
            LOGGER.log(Level.SEVERE, "Error en la capa DAO al crear un cliente.", ex);
            throw ex;
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws DaoException {
        try {
            ValidarCliente(cliente, false);
            clienteDao.modificar(cliente);
        } catch (ValidationException ex) {
            try {
                throw ex;
            } catch (ValidationException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        } catch (DaoException ex) {
            LOGGER.log(Level.SEVERE, "Error en la capa DAO al actualizar un cliente.", ex);
            throw ex;
        }
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
