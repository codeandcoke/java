package com.svalero.agenda.modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reunion extends Evento {

    public enum TipoAviso {
        PANTALLA, SMS, EMAIL
    }

    private String lugar;
    private LocalDate fecha;
    private TipoAviso aviso;
    private int tiempoPreaviso;

    public Reunion(String nombre, String descripcion, String lugar, LocalDate fecha, TipoAviso aviso, int tiempoPreaviso) {
        super(nombre, descripcion);
        this.lugar = lugar;
        this.fecha = fecha;
        this.aviso = aviso;
        this.tiempoPreaviso = tiempoPreaviso;
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public String getStringFecha() {
        return fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public int getYear() {
        return fecha.getYear();
    }

    public long getDiasFaltan() {
        return Duration.between(fecha, LocalDate.now()).toDays();
    }

    public TipoAviso getTipoAviso() {
        return aviso;
    }

    public String getStringTipoAviso() {
        return aviso.name();
    }

    @Override
    public void finalizar() {

    }

    @Override
    public String toString() {
        return super.toString() + " " + lugar;
    }
}
