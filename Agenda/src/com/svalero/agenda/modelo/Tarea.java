package com.svalero.agenda.modelo;

import java.time.LocalDate;

public class Tarea extends Evento {

    private boolean hecha;
    private LocalDate fechaRealizacion;

    public Tarea(String nombre, String descripcion) {
        super(nombre, descripcion);
        hecha = false;
        fechaRealizacion = null;
    }

    public boolean isHecha() {
        return hecha;
    }

    public void hacer() {
        hecha = true;
        fechaRealizacion = LocalDate.now();
    }

    @Override
    public String toString() {
        return super.toString() + " " + hecha;
    }

    @Override
    public void finalizar() {

    }
}
