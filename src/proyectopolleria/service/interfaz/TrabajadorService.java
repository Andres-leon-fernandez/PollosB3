/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Trabajador;

/**
 *
 * @author Andres
 */
public interface TrabajadorService {

    void registrarTrabajador(Trabajador trabajador) throws DaoException;

    void actualizarTrabajador(Trabajador trabajador) throws DaoException;

    void eliminarTrabajador(Trabajador trabajador) throws DaoException;

    Trabajador obtenerPorId(int id) throws DaoException;

    List<Trabajador> listarTodos() throws DaoException;

    Trabajador login(String usuario, String password) throws DaoException;

    List<Trabajador> listarUsuarios() throws DaoException;

    void actualizarDisponibilidad(int id, boolean disponible) throws DaoException;
    
    public List<Trabajador> listarMozos() throws DaoException;
    
    public void eliminarDni(String dni) throws DaoException;
}
