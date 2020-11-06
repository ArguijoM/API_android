package com.example.pm_mal_pr01;

public class contacto {
    private String nombre;
    private String usuario;
    private String contrasena;
    private int id;
    private int idCreador;
    public contacto(String nombre, String usuario, String contrasena,int id,int idcreador){
        this.contrasena = contrasena;
        this.usuario = usuario;
        this.nombre = nombre;
        this.id = id;
        this.idCreador= idcreador;
    }
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }
}
