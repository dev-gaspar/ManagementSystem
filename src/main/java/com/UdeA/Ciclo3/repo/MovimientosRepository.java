package com.UdeA.Ciclo3.repo;

import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

@Repository
public interface MovimientosRepository  extends JpaRepository<MovimientoDinero, Integer> {

    //Filtramos los movimientos por empleados
    @Query(value="SELECT * FROM movimientos WHERE empleado_id = ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByUsuario(Integer id);

    //Filtamos los movimientos por empresas
    @Query(value="SELECT * FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //Metodo para ver la suma de todos los movimientos
    @Query(value = "SELECT SUM(monto) FROM movimientos", nativeQuery = true)
    public abstract long sumarMonto();

    //Metodo para ver la suma de todos los movimientos
    @Query(value = "SELECT SUM(monto) FROM movimientos WHERE empleado_id =?1", nativeQuery = true)
    public abstract long sumarMontoPorEmpleado(Integer id);

    //Metodo para ver la suma de todos los movimientos
    @Query(value = "SELECT SUM(monto) FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract long sumarMontoPorEmpresa(Integer id);

    //Metodo que se trae el id de un usuario cuando tenga su correo
    @Query(value = "select id from empleado where correo=?1", nativeQuery = true)
    public abstract Integer idPorCorreo(String correo);

}
