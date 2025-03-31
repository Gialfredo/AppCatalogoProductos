package com.example.appcatalogoproductos;

public class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private float calificacion;
    private String imagenUrl;

    //    Constructor
    public Producto(String nombre, String categoria, double precio, float calificacion, String imagenUrl) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.calificacion = calificacion;
        this.imagenUrl = imagenUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
