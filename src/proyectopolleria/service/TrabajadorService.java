/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Trabajador;

/**
 *
 * @author Andres
 */
public interface TrabajadorService {

    Trabajador login(String usuario, String password) throws DaoException;

    List<Trabajador> listarUsuarios() throws DaoException;
}
