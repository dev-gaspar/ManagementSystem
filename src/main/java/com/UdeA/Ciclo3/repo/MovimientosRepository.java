package com.UdeA.Ciclo3.repo;

import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientosRepository  extends JpaRepository<MovimientoDinero, Integer> {

    //Filtramos los movimientos por empleados
    @Query(value="SELECT * FROM movimientos WHERE empleado_id = ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByUsuario(Integer id);

    //Filtamos los movimientos por empresas
    @Query(value="SELECT * FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //   @Query(value="SELECT * FROM movimientos WHERE empresa_id in (SELECT id FROM empresa WHERE usuario_id = ?1)", nativeQuery = true)
    // public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

}
