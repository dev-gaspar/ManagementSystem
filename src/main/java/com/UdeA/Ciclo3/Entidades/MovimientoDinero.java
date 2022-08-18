package com.UdeA.Ciclo3.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Movimientos")
public class MovimientoDinero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double monto;
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;

    public MovimientoDinero() {
    }

    public MovimientoDinero(double monto, String concepto, Empleado usuario) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = usuario;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public Empleado getUsuario() {
        return usuario;
    }
    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }
}
