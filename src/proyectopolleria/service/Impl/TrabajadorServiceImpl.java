    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package proyectopolleria.service.Impl;

    import java.util.List;
    import proyectopolleria.dao.DaoException;
    import proyectopolleria.dao.interfaces.TrabajadorDao;
    import proyectopolleria.model.Trabajador;
    import proyectopolleria.service.interfaz.TrabajadorService;

    /**
     *
     * @author andres
     */
    public class TrabajadorServiceImpl implements TrabajadorService {

        private TrabajadorDao dao;

        public TrabajadorServiceImpl(TrabajadorDao dao) {
            this.dao = dao;
        }

        @Override
        public void registrarTrabajador(Trabajador trabajador) throws DaoException {
            dao.crear(trabajador);
        }

        @Override
        public void actualizarTrabajador(Trabajador trabajador) throws DaoException {
            dao.modificar(trabajador);
        }

        @Override
        public void eliminarTrabajador(Trabajador trabajador) throws DaoException {
            dao.eliminar(trabajador);
        }

        @Override
        public Trabajador obtenerPorId(int id) throws DaoException {
            return dao.obtener(id);
        }

        @Override
        public List<Trabajador> listarTodos() throws DaoException {
            return dao.listarTodos();
        }

        @Override
        public Trabajador login(String u, String p) throws DaoException {
            return dao.login(u, p);
        }

        @Override
        public List<Trabajador> listarUsuarios() throws DaoException {
            return dao.listarUsuarios();
        }

        @Override
        public void actualizarDisponibilidad(int id, boolean disponible) throws DaoException {
            dao.actualizarDisponibilidad(id, disponible);
        }

        @Override
        public List<Trabajador> listarMozos() throws DaoException {
            return dao.listarMozos();
        }

    @Override
    public void eliminarDni(String dni) throws DaoException {
        dao.eliminarDni(dni);
    }
    }
