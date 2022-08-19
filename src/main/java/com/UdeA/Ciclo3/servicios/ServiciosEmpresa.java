package com.UdeA.Ciclo3.servicios;

import com.UdeA.Ciclo3.entidades.Empresa;
import com.UdeA.Ciclo3.repositorio.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiciosEmpresa {

    @Autowired
    RepositorioEmpresa respositorioEmpresa;//Creamos un objeto tipo RepositorioEmpresa para poder usar los metodos de dichas clase


    public List<Empresa> getAllEmpresas() {//Metodo retorna una lista de empresas usando metodos heredados de jpaRepository
        List<Empresa> empresas = new ArrayList<>();//Creamos la lista a retornar
        respositorioEmpresa.findAll().forEach(Empresa -> empresas.add(Empresa));//Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista
        return empresas;//Retornamos la lista
    }

    //Metodo retorna un objeto de tipo Empresa con el id
    public Empresa getEmpresaById(Integer id) {
        return respositorioEmpresa.findById(id).get();
    }

    //Metodo para guardar o actualizar objetos de tipo Empresa
    public boolean saveOrUpdateEmpresa(Empresa empresa) {
        Empresa emp = respositorioEmpresa.save(empresa);
        if (respositorioEmpresa.findById(emp.getId()) != null) {
            return true;
        }
        return false;
    }

    //Metodo para eliminar por id
    public boolean deleteEmpresa(Integer id){
        respositorioEmpresa.deleteById(id);
        if (getEmpresaById(id) != null){
            return false;
        }
        return true;
    }


}
