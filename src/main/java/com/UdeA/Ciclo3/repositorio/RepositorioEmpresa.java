package com.UdeA.Ciclo3.repositorio;

import com.UdeA.Ciclo3.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Anotacion para inidicar a Spring que esta clase es un respositorio
public interface RepositorioEmpresa extends JpaRepository<Empresa, Integer> {



}
