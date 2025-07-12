/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.controller;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Trabajador;
import proyectopolleria.service.interfaz.TrabajadorService;

/**
 *
 * @author Andres
 */
public class TrabajadorController {

    private TrabajadorService service;

    public TrabajadorController(TrabajadorService srv) {
        this.service = srv;
    }

    public Trabajador login(String u, String p) throws DaoException {
        return service.login(u, p);
    }

    public List<Trabajador> listarUsuarios() throws DaoException {
        return service.listarUsuarios();
    }

}
