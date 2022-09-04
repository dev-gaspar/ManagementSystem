package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.service.EmpleadoService;
import com.UdeA.Ciclo3.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerFull {

    //EMPRESAS
    @Autowired
    EmpresaService empresaService;

    @GetMapping("/enterprises")
    public List<Empresa> verEmpresas(){
        return  empresaService.getAllEmpresas();
    }

    @PostMapping("/enterprises")
    public Empresa guardarEmpresa(@RequestBody Empresa empresa){
        return empresaService.saveOrUpdateEmpresa(empresa);
    }

    @GetMapping(path = "enterprises/{id}")
    public Empresa buscarEmpresa(@PathVariable("id") Integer id){
        return empresaService.getEmpresaById(id);
    }

    @PatchMapping(path = "enterprises/{id}")
    public Empresa actualizarEmpresa(@PathVariable("id") Integer id, @RequestBody Empresa empresa){
        Empresa emp = empresaService.getEmpresaById(id);
        emp.setNombre(empresa.getNombre());
        emp.setDireccion(empresa.getDireccion());
        emp.setTelefono(empresa.getTelefono());
        emp.setNIT(empresa.getNIT());
        return empresaService.saveOrUpdateEmpresa(emp);
    }

    @DeleteMapping(path = "enterprises/{id}")
    public String eliminarEmpresa(@PathVariable("id") Integer id){
        if (empresaService.deleteEmpresa(id)){
            return "Empresa eliminada correctamente";
        }
        return "Fallo al eliminar empresa";
    }

    //EMPLEADOS
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public List<Empleado> verEmpleados(){
        return empleadoService.getAllEmpleados();
    }

    @PostMapping("/empleados")
    public Optional<Empleado> guardarEmpleado(@RequestBody Empleado empleado){
        return Optional.ofNullable(empleadoService.saveOrUpdateEmpleado(empleado));
    }

    @GetMapping("/empleados/{id}")
    public Optional<Empleado> buscarEmpleado(@PathVariable("id") Integer id){
        return empleadoService.getEmpleadoById(id);
    }

    @GetMapping("/enterprises/{id}/empleados")
    public ArrayList<Empleado> EmpleadosPorEmpresa(@PathVariable("id") Integer id){
        return empleadoService.getEmpleadoByEmpresa(id);
    }

}
