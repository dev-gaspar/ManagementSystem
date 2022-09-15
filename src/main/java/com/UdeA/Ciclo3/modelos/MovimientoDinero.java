package com.UdeA.Ciclo3.modelos;

import javax.persistence.*;

@Entity
@Table(name = "Movimientos")
public class MovimientoDinero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long monto;
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;




    public MovimientoDinero() {}

    public MovimientoDinero(long monto, String concepto) {
        this.monto = monto;
        this.concepto = concepto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



    public long monto() {
        return 0;
    }

    public String usuario() {
        return "Juan";
    }


}
