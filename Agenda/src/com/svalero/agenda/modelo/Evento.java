package com.svalero.agenda.modelo;

import java.io.Serializable;

public abstract class Evento implements Serializable {

    private String nombre;
    private String descripcion;

    public Evento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public abstract void finalizar();

    @Override
    public String toString() {
        return nombre + ": " + descripcion;
    }
}
