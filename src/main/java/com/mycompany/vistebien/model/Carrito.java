package com.mycompany.vistebien.model;

public class Carrito {

    private int idCarrito;
    private int idUsuario;
    private String estado;

    public Carrito() {
    }

    // Constructor antiguo
    public Carrito(int idUsuario) {
        this.idUsuario = idUsuario;
        this.estado = "pendiente";
    }

    // Constructor nuevo
    public Carrito(int idUsuario, String estado) {
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}