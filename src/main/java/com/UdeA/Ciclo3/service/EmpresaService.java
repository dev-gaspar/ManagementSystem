package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.repo.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//Le decimos a Spring que esta clase es de servicios
@Service
public class EmpresaService {
    @Autowired //Conectamos esta clase con el repository de Empresa
    EmpresaRepository empresaRepository; //Creamos un objeto tipo EmpresaRepository para poder usar los metodos que dicha clase hereda

    //Metodo que retornará la lista de empresas usando metodos heredados del jpaRepository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return empresaList;
    }

    //Metodo que me trae un objeto de tipo Empresa cuando cuento con el id de la misma
    public Empresa getEmpresaById(Integer id){
        Empresa empresa = null;
        try{
            empresa = empresaRepository.findById(id).get();
        }catch  (NoSuchElementException exception){
            //Entra al catch si no encontro la empresa y retorna null por que el try fallo
        }
        return empresa;
    }

    //Metodo para guardar o actualizar objetos de tipo Empresa
    public Empresa saveOrUpdateEmpresa(Empresa empresa){
        Empresa emp=empresaRepository.save(empresa);
        return emp;
    }

    //Metodo para eliminar empresas registradas teniendo el id
    public boolean deleteEmpresa(Integer id){
        empresaRepository.deleteById(id);
        if (getEmpresaById(id)!=null){
            return false;
        }
        return true;
    }

}
