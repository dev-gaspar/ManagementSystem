package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerFull {
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

}
