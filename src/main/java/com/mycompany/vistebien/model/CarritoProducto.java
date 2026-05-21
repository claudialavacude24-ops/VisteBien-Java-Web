package com.mycompany.vistebien.model;

public class CarritoProducto {

    private int idCarrito;     
    private int idProducto;
    private int idUsuario;
    private int cantidad;

    public CarritoProducto() {
    }

    public CarritoProducto(int idProducto, int idUsuario, int cantidad) {
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
