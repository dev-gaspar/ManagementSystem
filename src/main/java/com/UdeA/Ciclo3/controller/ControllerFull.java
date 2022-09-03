package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=empresaService.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        model.addAttribute("mensaje", mensaje);
        return "verEmpresas"; //Llamamos al HTML
    }

    @GetMapping({"/agregarEmpresa","/agregar-empresa"})
    public String agregarEmpresa(Model model , @ModelAttribute("mensaje") String mensaje){
        Empresa empresa = new Empresa();
        model.addAttribute("empresa",empresa);
        model.addAttribute("mensaje", mensaje);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa empresa, RedirectAttributes redirectAttributes){
        if (empresaService.saveOrUpdateEmpresa(empresa) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Save OK!");
            return "redirect:/empresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Save ERROR!");
        return "redirect:/agregar-empresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa empresa = empresaService.getEmpresaById(id);
        model.addAttribute("empresa",empresa);
        model.addAttribute("mensaje", mensaje);

        return "editarEmpresa";
    }

    @PostMapping("/ActualizarEmpresa")
    public String actualizarEmpresa(Empresa empresa, RedirectAttributes redirectAttributes){
        if (empresaService.saveOrUpdateEmpresa(empresa) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Update OK!");
            return "redirect:/empresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Update ERROR!");
        return "redirect:/EditarEmpresa";
    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empresaService.deleteEmpresa(id)){
            redirectAttributes.addFlashAttribute("mensaje", "Delete OK!");
            return "redirect:/empresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Delete ERROR!");
        return "redirect:/empresas";
    }

}
