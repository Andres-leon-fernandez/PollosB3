/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.interfaz;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.Receta;

/**
 *
 * @author Andres
 */
public interface RecetaService {

    void registrarReceta(Receta receta) throws DaoException;

    void actualizarReceta(Receta receta) throws DaoException;

    void eliminarReceta(Receta idReceta) throws DaoException;

    Receta obtenerRecetaPorId(Integer idReceta) throws DaoException;

    List<Receta> listarRecetas() throws DaoException;
}
