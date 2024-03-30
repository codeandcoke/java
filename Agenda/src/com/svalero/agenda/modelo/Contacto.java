package com.svalero.agenda.modelo;

public class Contacto extends Persona {

    private int horaPreferida;
    private String notas;

    public Contacto(String nombre, String apellidos, String telefono, String direccion, String email, int horaPreferida,
                    String notas) {
        super(nombre, apellidos, telefono, direccion, email);
        this.horaPreferida = horaPreferida;
        this.notas = notas;
    }

    public String getNotas() {
        return notas;
    }
}
