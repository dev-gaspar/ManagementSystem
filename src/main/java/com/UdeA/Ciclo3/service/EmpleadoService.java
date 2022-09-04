package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.repo.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> getAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        empleadoRepository.findAll().forEach(empleado -> empleados.add(empleado));
        return empleados;
    }

    public Optional<Empleado> getEmpleadoById(Integer id){
        return empleadoRepository.findById(id);
    }

    //Metodo busca empleados por empresa
    public ArrayList<Empleado> getEmpleadoByEmpresa(Integer id){
        return empleadoRepository.findByEmpresa(id);
    }

    public Empleado saveOrUpdateEmpleado(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public boolean deleteEmpleado(Integer id){
        empleadoRepository.deleteById(id);
        if (empleadoRepository.findById(id).isPresent()){
             return false;
        }
        return true;
    }
}