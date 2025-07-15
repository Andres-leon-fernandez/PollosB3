package proyectopolleria.controller;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.Impl.DaoManagerImpl;
import proyectopolleria.dao.interfaces.DaoManager;
import proyectopolleria.model.Trabajador;
import proyectopolleria.service.interfaz.TrabajadorService;
import java.sql.Connection;
import proyectopolleria.service.Impl.TrabajadorServiceImpl;

public class TrabajadorController {

    private TrabajadorService srvTrabajador;

    public TrabajadorController(Connection conn) {
        DaoManager daoManager = new DaoManagerImpl(conn);
        this.srvTrabajador = new TrabajadorServiceImpl(daoManager.getTrabajadorDao());
    }

    public void registrarTrabajador(Trabajador trabajador) {
        try {
            srvTrabajador.registrarTrabajador(trabajador);
            System.out.println("? Trabajador registrado.");
        } catch (DaoException e) {
            System.err.println("? Error al registrar trabajador: " + e.getMessage());
        }
    }

    public void actualizarTrabajador(Trabajador trabajador) {
        try {
            srvTrabajador.actualizarTrabajador(trabajador);
            System.out.println("? Trabajador actualizado.");
        } catch (DaoException e) {
            System.err.println("? Error al actualizar trabajador: " + e.getMessage());
        }
    }

    public void eliminarTrabajador(Trabajador trabajador) {
        try {
            srvTrabajador.eliminarTrabajador(trabajador);
            System.out.println("?? Trabajador eliminado.");
        } catch (DaoException e) {
            System.err.println("? Error al eliminar trabajador: " + e.getMessage());
        }
    }

    public Trabajador obtenerPorId(int id) {
        try {
            return srvTrabajador.obtenerPorId(id);
        } catch (DaoException e) {
            System.err.println("? Error al obtener trabajador por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Trabajador> listarTrabajadores() {
        try {
            return srvTrabajador.listarTodos();
        } catch (DaoException e) {
            System.err.println("? Error al listar trabajadores: " + e.getMessage());
            return null;
        }
    }

    public Trabajador login(String usuario, String password) {
        try {
            return srvTrabajador.login(usuario, password);
        } catch (DaoException e) {
            System.err.println("? Error en login: " + e.getMessage());
            return null;
        }
    }

    public List<Trabajador> listarUsuarios() {
        try {
            return srvTrabajador.listarUsuarios();
        } catch (DaoException e) {
            System.err.println("? Error al listar usuarios: " + e.getMessage());
            return null;
        }
    }

    public List<Trabajador> listarMozos() throws DaoException {
        try {
            return srvTrabajador.listarMozos();
        } catch (DaoException e) {
            System.err.println("? Error al listar Mozos: " + e.getMessage());
            return null;
        }
    }
}
