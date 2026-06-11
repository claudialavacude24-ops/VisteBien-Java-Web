package com.mycompany.vistebien.model;

public class ItemCarrito {

    private int idProducto;
    private String nombreProducto;
    private double precio;
    private int cantidad;
    private String imagen;

    public ItemCarrito() {
    }

    public ItemCarrito(
            int idProducto,
            String nombreProducto,
            double precio,
            int cantidad,
            String imagen) {

        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getSubtotal() {
        return precio * cantidad;
    }
}