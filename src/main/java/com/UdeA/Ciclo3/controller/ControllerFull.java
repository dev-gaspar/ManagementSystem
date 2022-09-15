package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.modelos.Usuario;
import com.UdeA.Ciclo3.service.EmpresaService;
import com.UdeA.Ciclo3.service.MovimientoDineroService;
import com.UdeA.Ciclo3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerFull {
    @Autowired
    EmpresaService empresaService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MovimientoDineroService movimientoDineroService;


    //EMPRESA


    /*
       Realizamos todas las consutlas y los metodos en las diferentes rutass

       - listar todos
       - listar por id
       - guardar o editar todos
       - guardar o editar por id
       - Eliminamos por id

     */
    @GetMapping("/enterprises")  //Listar todos las empresas
    public List<Empresa> verEmpresas() {
        return empresaService.getAllEmpresa();
    }
    //Guardamos y editamos todas las empresas
    @PostMapping("/enterprises")
    public Empresa guardarEmpresa(@RequestBody Empresa emp){
        return this.empresaService.saveOrUpdateEmpresa(emp);
    }
    //Busacamos empresa por id
    @GetMapping(path = "enterprises/{id}")
    public Empresa empresaPorId(@PathVariable("id") Integer id) {
        return this.empresaService.getEmpresaById(id);
    }

    //Editamos y actualizamos la   empresa por id
    @PatchMapping("/enterprises/{id}")
    public Empresa actualizarEmpresa(@PathVariable("id") Integer id, @RequestBody Empresa empresa ){
       Empresa emp = empresaService.getEmpresaById(id);
       emp.setNombre(empresa.getNombre());
       emp.setDireccion(empresa.getDireccion());
       emp.setTelefono(empresa.getTelefono());
       emp.setNIT(empresa.getNIT());

       return empresaService.saveOrUpdateEmpresa(emp);
    }

    @DeleteMapping(path="enterprises/{id}")
    public String DeleteEmpresa(@PathVariable("id") Integer id) {
        boolean respuesta = this.empresaService.deleteEmpresa(id);

        if (respuesta) {
            return "Empresa eliminada correctamente - id: " + id;
        }
        return "No fue posible Eliminar la empresa - id: " + id;
    }

    //USUARIO

    //ver usuarios
    @GetMapping("/users")
    public List<Usuario> verUsuarios() {
        return usuarioService.getAllUsuario();
    }
    //guardar y editar usuarios
    @PostMapping("/users")
    public Optional<Usuario> guardarUsuarios(@RequestBody Usuario users) {
      return Optional.ofNullable(this.usuarioService.saveOrUpdateUsuario(users));
    }

    @GetMapping(path = "user/{id}")
    public Optional<Usuario> usuarioPorId(@PathVariable("id") Integer id){
     return this.usuarioService.getUsuarioById(id);
    }


    @GetMapping("/enterprises/{id}/users")
    public ArrayList<Usuario> UsuarioPorEmpresa(@PathVariable("id") Integer id){
        return this.usuarioService.obtenerPorEmpresa(id);
    }

    @PatchMapping("/user/{id}")
    public Usuario actualizarUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario){
        Usuario users=usuarioService.getUsuarioById(id).get();
        users.setNombre(usuario.getNombre());
        users.setCorreo(usuario.getCorreo());
        users.setEmpresa(usuario.getEmpresa());
        users.setRol(usuario.getRol());
        return usuarioService.saveOrUpdateUsuario(users);
    }

    @DeleteMapping("/user/{id}") //Metodo para eliminar usuario por id
    public String DeleteUsuario(@PathVariable("id") Integer id){
        boolean respuesta=usuarioService.deleteUsuario(id); //eliminamos usando el servicio de nuestro service
        if (respuesta){
            return "Usuario eliminada correctamente - id: "+id;
        }
        return "No fue posible eliminar el Usuario - id: "+id;
    }

    //MOVIMIENTO

    @GetMapping("/movements") //Consultar todos los movimientos
    public List<MovimientoDinero> verMovimientos(){
        return movimientoDineroService.getAllMovimientos();
    }

    @PostMapping("/movements")
    public MovimientoDinero guardarMovimiento(@RequestBody MovimientoDinero movimiento){
        return movimientoDineroService.saveOrUpdateMovimiento(movimiento);
    }

    @GetMapping("/movements/{id}") //Consultar movimiento por id
    public MovimientoDinero movimientoPorId(@PathVariable("id") Integer id){
        return movimientoDineroService.getMovimientoById(id);
    }

    @PatchMapping("/movements/{id}")//Editar o actualizar un movimiento
    public MovimientoDinero actualizarMovimiento(@PathVariable("id") Integer id, @RequestBody MovimientoDinero movimiento){
        MovimientoDinero mov=movimientoDineroService.getMovimientoById(id);
        mov.setConcepto(movimiento.getConcepto());
        mov.setMonto(movimiento.getMonto());
        mov.setUsuario(movimiento.getUsuario());
        return movimientoDineroService.saveOrUpdateMovimiento(mov);
    }

    @DeleteMapping("/movements/{id}")
    public String deleteMovimiento(@PathVariable("id") Integer id){
        boolean respuesta= movimientoDineroService.deleteById(id);
        if (respuesta){
            return "Se elimino correctamente el movimiento con id " +id;
        }
        return "No se pudo eliminar el movimiento con id "+id;
    }

    @GetMapping("/user/{id}/movements") //Consultar movimientos por id del empleado
    public ArrayList<MovimientoDinero> movimientosPorEmpleado(@PathVariable("id") Integer id){
        return movimientoDineroService.obtenerPorUsuario(id);
    }

    //ver movimiento por empresa
    @GetMapping("/enterprises/{id}/movements") //Consultar movimientos que pertenecen a una empresa por el id de la empresa
    public ArrayList<MovimientoDinero> movimientosPorEmpresa(@PathVariable("id") Integer id){
        return movimientoDineroService.obtenerPorEmpresa(id);
    }
    @PostMapping("/enterprises/{id}/movements")
    public MovimientoDinero guardarMovimientoPorIdUsuario(@RequestBody MovimientoDinero movimiento, @PathVariable("id") Integer id){
        return movimientoDineroService.saveOrUpdateMovimiento(movimiento);
    }

    @PatchMapping("/enterprises/{id}/movements")
    public MovimientoDinero actualizarMovimientoDinero(@RequestBody MovimientoDinero movimiento, @PathVariable("id") Integer id ){
        MovimientoDinero mov = movimientoDineroService.getMovimientoById(id);
        mov.setMonto(movimiento.getMonto());
        mov.setConcepto(movimiento.getConcepto());
        mov.setUsuario(movimiento.getUsuario());
        return movimientoDineroService.saveOrUpdateMovimiento(mov);
    }

    @DeleteMapping("/enterprises/{id}/movements")
    public String DeleteMovimientoByEmpresa(@PathVariable("id") Integer id){
        boolean respuesta= movimientoDineroService.deleteById(id);
        if (respuesta){
            return "Movimiento eliminado correctamente - id: " +id;
        }
        return "UNo fue posible eliminar el Movimiento - id: "+id;


    }




}


