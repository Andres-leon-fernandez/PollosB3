package proyectopolleria.service.Impl;

import java.util.List;
import javax.swing.JOptionPane;
import proyectopolleria.dao.DaoException;
import proyectopolleria.service.interfaz.ProveedorService;
import proyectopolleria.dao.interfaces.ProveedorDao;
import proyectopolleria.model.Proveedor;

public class ProveedorServiceImp implements ProveedorService {

    private ProveedorDao dao;

    public ProveedorServiceImp(ProveedorDao dao) {
        this.dao = dao;
    }

    @Override
    public void registrarProveedor(Proveedor proveedor) throws DaoException {
//        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isBlank()) {
//            throw new DaoException("El nombre del proveedor no puede estar vacío.");
//        }
//        String ruc = proveedor.getRuc();
//        if (ruc == null || !ruc.matches("\\d{11}")) {
//            throw new DaoException("El RUC debe tener exactamente 11 dígitos numéricos.");
//        }
//        String telefono = proveedor.getTelefono();
//        if (telefono == null || !telefono.matches("9\\d{8}")) {
//            throw new DaoException("El teléfono debe tener 9 dígitos y empezar con 9.");
//        }
//        String correo = proveedor.getCorreo();
//        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
//        if (correo == null || !correo.matches(emailRegex)) {
//            throw new DaoException("El correo electrónico no tiene un formato válido.");
//        }
//        if (proveedor.getDireccion() == null || proveedor.getDireccion().trim().isBlank()) {
//            throw new DaoException("La dirección no puede estar vacía.");
//        }
        dao.crear(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) throws DaoException {
        dao.modificar(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) throws DaoException {
        dao.eliminar(proveedor);
    }

    @Override
    public Proveedor obtenerPorId(int id) throws DaoException {
        return dao.obtener(id);
    }

    @Override
    public List<Proveedor> listarTodos() throws DaoException {
        return dao.listarTodos();
    }

}
