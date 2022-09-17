package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repo.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {

    //listar, listar por id, actualizar o guardar y eliminar
    @Autowired
    MovimientosRepository movimientoDineroRepository;

    //listamos todos los movimientos
    public List<MovimientoDinero> getAllMovimientos() {
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientoDineroRepository.findAll().forEach(movimiento -> movimientosList.add(movimiento));
        return movimientosList;
    }

    //Buscamos un movimiento por id
    public MovimientoDinero getMovimientoById(Integer id) {
        return movimientoDineroRepository.findById(id).get();
    }

    //actualizamos o guardamos un movimiento
    public boolean saveOrUpdateMovimiento(MovimientoDinero movimientoDinero){
        MovimientoDinero mov = movimientoDineroRepository.save(movimientoDinero);
        return movimientoDineroRepository.existsById(mov.getId());
    }
    //eliminamos un movimiento por id
    public boolean deleteById(Integer id){
        movimientoDineroRepository.deleteById(id);
        if(this.movimientoDineroRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<MovimientoDinero> obtenerPorUsuario(Integer id){
        return movimientoDineroRepository.findByUsuario(id);
    }
    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer id){
        return movimientoDineroRepository.findByEmpresa(id);
    }


}
