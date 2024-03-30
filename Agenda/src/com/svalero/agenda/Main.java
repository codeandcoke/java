package com.svalero.agenda;

import com.svalero.agenda.modelo.Contacto;
import com.svalero.agenda.modelo.Persona;
import com.svalero.agenda.modelo.Usuario;

import java.sql.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.mostrarMenu();

        Contacto contacto = new Contacto("", "", "", "", "", 10, "");
        Usuario usuario = new Usuario("", "", "", "", "", "", "", Usuario.TipoUsuario.USUARIO);

        ArrayList<Persona> personas = new ArrayList<>();
        personas.add(contacto);
        personas.add(usuario);
    }
}
