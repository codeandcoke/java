package com.svalero.agenda;

import com.svalero.agenda.modelo.Evento;
import com.svalero.agenda.modelo.Reunion;
import com.svalero.agenda.modelo.Tarea;
import com.svalero.agenda.util.Ficheros;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static com.svalero.agenda.util.Ficheros.EVENTOS_DAT;

public class Agenda {

    private Scanner teclado;
    private ArrayList<Evento> eventos;

    public Agenda() {
        teclado = new Scanner(System.in);
        eventos = new ArrayList<>();

        if (new File(EVENTOS_DAT).exists()) {
            eventos = Ficheros.cargar(EVENTOS_DAT);
            if (eventos == null) {
                System.out.println("Se ha producido un error al intentar cargar los datos");
                System.exit(1);
            }
        }
    }

    public void mostrarMenu() {
        do {
            System.out.println("AGENDA SAN VALERO");
            System.out.println("1. Añadir una tarea");
            System.out.println("2. Modificar una tarea");
            System.out.println("3. Ver tareas");
            System.out.println("4. Buscar una tarea");
            System.out.println("5. Eliminar una tarea");
            System.out.println("6. Añadir una reunión");
            System.out.println("7. Ver reuniones");
            System.out.println("8. Ver todos los eventos");
            System.out.println("q. Salir");
            System.out.print("Opción: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    anadirTarea();
                    break;
                case "2":
                    modificarTarea();
                    break;
                case "3":
                    verTareas();
                    break;
                case "4":
                    // TODO Buscar una tarea por nombre
                    break;
                case "5":
                    eliminarTarea();
                    break;
                case "6":
                    anadirReunion();
                    break;
                case "7":
                    verReuniones();
                    break;
                case "8":
                    verTodos();
                default:
            }

            if (opcion.equals("q"))
                break;
        } while (true);
    }

    private void verTodos() {
        for (Evento evento : eventos) {
            System.out.println(evento.toString());
        }
    }

    private void anadirTarea() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Descripción: ");
        String descripcion = teclado.nextLine();
        Tarea tarea = new Tarea(nombre, descripcion);
        eventos.add(tarea);
        Ficheros.guardar(eventos, EVENTOS_DAT);
    }

    private void modificarTarea() {
        boolean modificada = false;
        System.out.print("Nombre?:");
        String nombre = teclado.nextLine();
        for (Evento evento : eventos) {
            if (evento instanceof Tarea tarea) {
                if (tarea.getNombre().equals(nombre)) {
                    System.out.print("Nuevo nombre:");
                    String nuevoNombre = teclado.nextLine();
                    System.out.print("Nueva descripción:");
                    String nuevaDescripcion = teclado.nextLine();
                    tarea.setNombre(nuevoNombre);
                    tarea.setDescripcion(nuevaDescripcion);
                    modificada = true;
                    break;
                }
            }
        }
        if (!modificada) {
            System.out.println("No se ha podido encontrar una tarea con ese nombre");
        }
    }

    private void verTareas() {
        for (Evento evento: eventos) {
            if (evento instanceof Tarea tarea)
                System.out.println(tarea.getNombre() + ": " + tarea.getDescripcion());
        }
    }

    private void eliminarTarea() {
        System.out.print("Nombre?: ");
        String nombre = teclado.nextLine();
        // TODO Preguntar al usuario si está seguro
        eventos.removeIf(tarea -> tarea.getNombre().equals(nombre));
        // TODO Confirmar al usuario que se ha eliminado la tarea
        Ficheros.guardar(eventos, EVENTOS_DAT);
    }

    private void anadirReunion() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.println("Descripción: ");
        String descripcion = teclado.nextLine();
        System.out.println("Lugar: ");
        String lugar = teclado.nextLine();
        System.out.println("Fecha (dd-MM-yyyy): ");
        String fecha = teclado.nextLine();
        LocalDate fechaEvento = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println("Tipo de aviso [pantalla,sms,email]: ");
        String tipoAviso = teclado.nextLine();
        Reunion.TipoAviso tipoAvisoEvento = Reunion.TipoAviso.valueOf(tipoAviso.toUpperCase());
        System.out.println("Tiempo de preaviso (por defecto 30 minutos): ");
        String tiempoPreaviso = teclado.nextLine();
        int tiempoPreavisoEvento = 30;
        if (!tiempoPreaviso.isEmpty())
            tiempoPreavisoEvento = Integer.parseInt(tiempoPreaviso);

        Reunion reunion = new Reunion(nombre, descripcion, lugar, fechaEvento, tipoAvisoEvento, tiempoPreavisoEvento);
        eventos.add(reunion);
        Ficheros.guardar(eventos, EVENTOS_DAT);
    }

    private void verReuniones() {
        for (Evento evento : eventos) {
            if (evento instanceof Reunion reunion)
                System.out.println(reunion.getNombre() + ": " + reunion.getStringFecha() + " | " + reunion.getStringTipoAviso());
        }
    }
}
