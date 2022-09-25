package com.UdeA.Ciclo3.security;

import com.UdeA.Ciclo3.handler.CustomSuccessHandler;
import com.UdeA.Ciclo3.repo.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select correo, contraseña, estado from empleado where correo=?" )
                .authoritiesByUsernameQuery("select correo, rol from empleado where correo=?");

    }


    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/empresas/**").hasRole("ADMIN")
                .antMatchers("/empleados/**").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole("ADMIN","USER")
                .antMatchers("/movimientos/**").hasAnyRole("ADMIN","USER")
                .and()
                .formLogin().successHandler(customSuccessHandler)
                .and()
                .exceptionHandling().accessDeniedPage("/denegado")
                .and()
                .logout().permitAll().and().csrf();
    }

}
