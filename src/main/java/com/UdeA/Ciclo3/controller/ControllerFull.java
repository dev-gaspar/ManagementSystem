package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ControllerFull {
    @Autowired
    EmpresaService empresaService;

    @GetMapping("/")
    public String index(){
        return "index";
    }


    @GetMapping ({"/empresas","/lista-empresas"})
    public String viewEmpresas(Model model){
        List<Empresa> listaEmpresas=empresaService.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        return "verEmpresas"; //Llamamos al HTML
    }

    @GetMapping({"/agregarEmpresa","/agregar-empresa"})
    public String agregarEmpresa(Model model){
        Empresa empresa = new Empresa();
        model.addAttribute("empresa",empresa);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa empresa, RedirectAttributes redirectAttributes){
        if (empresaService.saveOrUpdateEmpresa(empresa) == true){
            return "redirect:/empresas";
        }
        return "redirect:/agregar-empresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id){
        Empresa empresa = empresaService.getEmpresaById(id);
        model.addAttribute("empresa",empresa);

        return "editarEmpresa";
    }

    @PostMapping("/ActualizarEmpresa")
    public String actualizarEmpresa(Empresa empresa){
        if (empresaService.saveOrUpdateEmpresa(empresa) == true){
            return "redirect:/empresas";
        }
        return "redirect:/agregar-empresa";
    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id){
        empresaService.deleteEmpresa(id);
        return "redirect:/empresas";
    }

}
