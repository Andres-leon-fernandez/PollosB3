/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectopolleria.dao.interfaces;

import java.util.List;
import proyectopolleria.dao.DaoException;
import proyectopolleria.model.RecetaInsumo;

/**
 *
 * @author andres
 */
public interface RecetaInsumoDAO {

    void insertarInsumoEnReceta(RecetaInsumo recetaInsumo) throws DaoException;

    List<RecetaInsumo> obtenerInsumosDeReceta(int idReceta) throws DaoException;
}
