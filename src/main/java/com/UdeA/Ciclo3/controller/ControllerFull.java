package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repo.MovimientosRepository;
import com.UdeA.Ciclo3.service.EmpleadoService;
import com.UdeA.Ciclo3.service.EmpresaService;
import com.UdeA.Ciclo3.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class ControllerFull {

    //Empresa
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

    //Empleados
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping ({"/empleados","/lista-empleados"})
    public String viewEmpleados(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados=empleadoService.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados"; //Llamamos al HTML
    }

    @GetMapping({"/agregarEmpleado","/agregar-empleado"})
    public String agregarEmpleado(Model model , @ModelAttribute("mensaje") String mensaje){
        Empleado empleado = new Empleado();
        model.addAttribute("empleado",empleado);
        model.addAttribute("mensaje", mensaje);

        List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);

        return "agregarEmpleado";
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empleado, RedirectAttributes redirectAttributes){

        String passEncriptada = passwordEncoder().encode(empleado.getContraseña());
        empleado.setContraseña(passEncriptada);

        if (empleadoService.saveOrUpdateEmpleado(empleado) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Save OK!");
            return "redirect:/empleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Save ERROR!");
        return "redirect:/agregar-empleado";
    }

    @GetMapping("/EditarEmpleado/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado empleado = empleadoService.getEmpleadoById(id);
        model.addAttribute("empleado",empleado);
        model.addAttribute("mensaje", mensaje);

        List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);

        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String actualizarEmpleado(Empleado empleado, RedirectAttributes redirectAttributes){

        if (!empleado.getContraseña().trim().equals("")){
            String passEncriptada = passwordEncoder().encode(empleado.getContraseña());
            empleado.setContraseña(passEncriptada);
        } else{
            empleado.setContraseña(empleadoService.getEmpleadoById(empleado.getId()).getContraseña());
        }

        if (empleadoService.saveOrUpdateEmpleado(empleado) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Update OK!");
            return "redirect:/empleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Update ERROR!");
        return "redirect:/EditarEmpleado";
    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empleadoService.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje", "Delete OK!");
            return "redirect:/empleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Delete ERROR!");
        return "redirect:/empleados";
    }

    @GetMapping ("/Empresa/{id}/Empleados")
    public String viewEmpleadosPorEmpresa(Model model,@PathVariable Integer id ,@ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados=empleadoService.obtenerPorEmpresa(id);
        model.addAttribute("emplelist",listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados"; //Llamamos al HTML
    }

    //MOVIMIENTOS
    @Autowired
    MovimientosService movimientosService;
    @Autowired
    MovimientosRepository movimientosRepository;
    @GetMapping ({"/movimientos","/lista-movimientos"})
    public String viewMovimientos(@RequestParam(value = "pagina", required = false, defaultValue = "0") int NumeroPagina,
                                  @RequestParam(value = "medida", required = false, defaultValue = "5") int medida,
                                  Model model, @ModelAttribute("mensaje") String mensaje){

        //List<MovimientoDinero> listaMovimientos=movimientosService.getAllMovimientos();
        Page<MovimientoDinero> paginasMovimientos = movimientosRepository.findAll(PageRequest.of(NumeroPagina, medida));

        long sumaMontos = movimientosService.getSumaMontos();

        //model.addAttribute("movlist",listaMovimientos);
        model.addAttribute("movlist", paginasMovimientos.getContent());
        model.addAttribute("paginas", new int[paginasMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);

        model.addAttribute("sumaMontos", sumaMontos);
        model.addAttribute("mensaje", mensaje);

        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping({"/agregarMovimiento","/agregar-movimiento"})
    public String agregarMovimiento(Model model , @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento = new MovimientoDinero();
        model.addAttribute("movimiento",movimiento);
        model.addAttribute("mensaje", mensaje);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idEmpleado = movimientosService.IdPorCorreo(correo);
        model.addAttribute("idEmpleado", idEmpleado);

        return "agregarMovimiento";
    }

    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero movimiento, RedirectAttributes redirectAttributes){
        if (movimientosService.saveOrUpdateMovimiento(movimiento) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Save OK!");
            return "redirect:/movimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Save ERROR!");
        return "redirect:/agregar-movimiento";
    }

    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento = movimientosService.getMovimientoById(id);
        model.addAttribute("movimiento", movimiento);
        model.addAttribute("mensaje", mensaje);

        List<Empleado> listaEmpleados =empleadoService.getAllEmpleado();
        model.addAttribute("emplelist", listaEmpleados);

        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String actualizarMovimiento(MovimientoDinero movimiento, RedirectAttributes redirectAttributes){
        if (movimientosService.saveOrUpdateMovimiento(movimiento) == true){
            redirectAttributes.addFlashAttribute("mensaje", "Update OK!");
            return "redirect:/movimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Update ERROR!");
        return "redirect:/EditarMovimiento";
    }

    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (movimientosService.deleteById(id)){
            redirectAttributes.addFlashAttribute("mensaje", "Delete OK!");
            return "redirect:/movimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Delete ERROR!");
        return "redirect:/movimientos ";
    }

    @GetMapping ("/Empleado/{id}/Movimientos")
    public String viewMovimeintosPorEmpleado(Model model,@PathVariable Integer id ,@ModelAttribute("mensaje") String mensaje){
        List<MovimientoDinero> listaMovimientos=movimientosService.obtenerPorUsuario(id);
        long sumaMontos = movimientosService.getSumaMontosByEmpleado(id);

        model.addAttribute("movlist",listaMovimientos);
        model.addAttribute("sumaMontos", sumaMontos);
        model.addAttribute("mensaje", mensaje);
        return "verMovimientos";
    }

    @GetMapping ("/Empresa/{id}/Movimientos")
    public String viewMovimeintosPorEmpresa(Model model,@PathVariable Integer id ,@ModelAttribute("mensaje") String mensaje){
        List<MovimientoDinero> listaMovimientos=movimientosService.obtenerPorEmpresa(id);
        long sumaMontos = movimientosService.getSumaMontosByEmpresa(id);

        model.addAttribute("movlist",listaMovimientos);
        model.addAttribute("sumaMontos", sumaMontos);
        model.addAttribute("mensaje", mensaje);
        return "verMovimientos";
    }

    //metodo redirecciona a pagina de no autorizado
    @RequestMapping(value= "/denegado")
    public String denegado(){
        return "denegado";
    }

    //Método para encriptar contraseña
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}