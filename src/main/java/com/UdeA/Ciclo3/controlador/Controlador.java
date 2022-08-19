package com.UdeA.Ciclo3.controlador;

import com.UdeA.Ciclo3.entidades.Empresa;
import com.UdeA.Ciclo3.servicios.ServiciosEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Controlador {
    @Autowired
    ServiciosEmpresa serviciosEmpresa;

    @GetMapping({"/empresas","/ver-empresas"})
    public String viewEmpresas(Model model){
        List<Empresa> empresas = serviciosEmpresa.getAllEmpresas();
        model.addAttribute("empresas",empresas);
        return "ver-empresas";
    }


}
