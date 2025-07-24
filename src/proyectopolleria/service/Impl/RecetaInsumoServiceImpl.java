/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.service.Impl;

import java.util.List;
import proyectopolleria.DTO.DetalleInsumoDTO;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.interfaces.RecetaInsumoDAO;
import proyectopolleria.model.RecetaInsumo;
import proyectopolleria.service.interfaz.RecetaInsumoService;

/**
 *
 * @author andres
 */
public class RecetaInsumoServiceImpl implements RecetaInsumoService {

    private final RecetaInsumoDAO recetaInsumoDao;

    public RecetaInsumoServiceImpl(RecetaInsumoDAO recetaInsumoDao) {
        this.recetaInsumoDao = recetaInsumoDao;
    }

    @Override
    public List<RecetaInsumo> obtenerInsumosDeReceta(int idReceta) throws DaoException {
        return recetaInsumoDao.obtenerInsumosDeReceta(idReceta);
    }

    @Override
    public void agregarInsumoAReceta(RecetaInsumo recetaInsumo) throws DaoException {
        recetaInsumoDao.insertarInsumoEnReceta(recetaInsumo);
    }

    @Override
    public List<DetalleInsumoDTO> listarInsumosConProveedorPorProducto(int idProducto) throws DaoException {
        return recetaInsumoDao.listarInsumosConProveedorPorProducto(idProducto);
    }
}
