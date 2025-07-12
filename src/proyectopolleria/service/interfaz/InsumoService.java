/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Insumo;

/**
 *
 * @author Andres
 */
public interface InsumoService {
        void registrarInsumo(Insumo insumo) throws DaoException;

    void actualizarInsumo(Insumo insumo) throws DaoException;

    void eliminarInsumo(Insumo insumo) throws DaoException;

    Insumo obtenerPorId(int id) throws DaoException;

    List<Insumo> listarTodos() throws DaoException;
}
