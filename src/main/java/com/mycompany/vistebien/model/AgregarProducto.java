package com.mycompany.vistebien.model;

public class AgregarProducto {

    private int idAgregarProducto;
    private int idProducto;
    private int idUsuario;
    private int cantidad;
    private String imagen; // <-- nuevo campo

    // Getters y setters
    public int getIdAgregarProducto() {
        return idAgregarProducto;
    }

    public void setIdAgregarProducto(int idAgregarProducto) {
        this.idAgregarProducto = idAgregarProducto;
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

    // Nuevo campo imagen
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
