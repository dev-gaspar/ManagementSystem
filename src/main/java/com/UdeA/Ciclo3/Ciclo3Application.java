package com.UdeA.Ciclo3;

import com.UdeA.Ciclo3.entidades.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Ciclo3Application {

	@GetMapping("/hello")
	public String hello(){
		return "Hola Ciclo 3... Saldremos vivos de esto!";
	}

	@GetMapping("/test")
	public String test(){
		Empresa empresa = new Empresa("Panda Soft", "Calle la geta", "3116347712", "8002211345");

		empresa.setDireccion("Calle al mar");

		return "Id: " + empresa.getId() + "\n"
				+"Nombre: " + empresa.getNombre() + "\n"
				+"Direccion: " + empresa.getDireccion() + "\n"
				+"Telefono: " + empresa.getTelefono() + "\n"
				+"NIT: " + empresa.getNIT() + "\n";
	}


	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);

	}

}
