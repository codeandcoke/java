package com.svalero.agenda.modelo;

public class Usuario extends Persona {

    public enum TipoUsuario {
        INVITADO, USUARIO, ADMINISTRADOR
    }

    private String nombreUsuario;
    private String contrasena;
    private TipoUsuario tipoUsuario;

    public Usuario(String nombre, String apellidos, String telefono, String direccion, String email, String nombreUsuario,
                   String contrasena, TipoUsuario tipoUsuario) {
        super(nombre, apellidos, telefono, direccion, email);
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

}
