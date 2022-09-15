package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.repo.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {
    @Autowired // nos conectamos al repositorio empresa
    EmpresaRepository empresaRepository;

    public List<Empresa> getAllEmpresa(){ //Listar todas las empresas usando heredados de empresa repository

        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa));
        return empresaList;
    }

    //Metodo que me trae un objeto de tipo empresa
    public Empresa getEmpresaById(Integer id) {
        return empresaRepository.findById(id).get();
    }

    //Guardar o actualizar datos de tipo empresa
    public Empresa saveOrUpdateEmpresa(Empresa empresa) {
        Empresa emp = empresaRepository.save(empresa);
        return emp;
    }

    public boolean deleteEmpresa(Integer id) {
        empresaRepository.deleteById(id);

        if(empresaRepository.findById(id).isPresent()) {
            return false;
        }
        return true;
    }

}
