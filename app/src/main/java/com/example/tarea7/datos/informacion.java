package com.example.tarea7.datos;
public class informacion {
    private Integer id;
    private String url,descripcion;
    public informacion(){
    }
    public informacion(Integer id, String url, String descripcion) {
        this.id = id;
        this.url = url;
        this.descripcion = descripcion;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}