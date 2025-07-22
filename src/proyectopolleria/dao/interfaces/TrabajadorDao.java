package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Trabajador;

public interface TrabajadorDao extends Dao<Trabajador, Integer> {

    Trabajador login(String usuario, String password) throws DaoException;

    List<Trabajador> listarUsuarios() throws DaoException;

    void actualizarDisponibilidad(int id, boolean disponible) throws DaoException;

    public List<Trabajador> listarMozos() throws DaoException;

    void eliminarDni(String dni) throws DaoException;

    public List<Trabajador> listarDeliveryDisponible() throws DaoException;

    public List<Trabajador> listarMozoDisponible() throws DaoException;
    
}
