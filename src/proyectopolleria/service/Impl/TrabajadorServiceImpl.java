/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.TrabajadorDao;
import proyectopolleria.model.Trabajador;
import proyectopolleria.service.TrabajadorService;

/**
 *
 * @author andres
 */
public class TrabajadorServiceImpl implements TrabajadorService{

    private TrabajadorDao dao;

    public TrabajadorServiceImpl(TrabajadorDao dao) {
        this.dao = dao;
    }

    @Override
    public Trabajador login(String u, String p) throws DaoException {
        return dao.login(u, p);
    }

    @Override
    public List<Trabajador> listarUsuarios() throws DaoException {
        return dao.listarUsuarios();
    }
}
