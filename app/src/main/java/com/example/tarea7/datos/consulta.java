package com.example.tarea7.datos;
public class consulta {
    public static final String persona="personas2";
    public static final String id="id";
    public static final String url= "url";
    public static final String descripcion="descripcion";
    public static final String DataBase="lista1";
    public static final String CrearTablaUsuario="CREATE TABLE "+persona+" "+
            "("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            url+" TEXT,"+
            descripcion+" TEXT"+
            ")";
    public static final String DropTableUsuario="DROP TABLE IF EXISTS "+persona;
}
