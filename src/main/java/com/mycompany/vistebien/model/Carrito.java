package com.mycompany.vistebien.model;

public class Carrito {

    private int idCarrito;
    private int idUsuario;

    public Carrito() {
    }

    public Carrito(int idUsuario) {
        this.idUsuario = idUsuario;
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
}
