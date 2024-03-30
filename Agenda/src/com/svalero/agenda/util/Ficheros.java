package com.svalero.agenda.util;

import com.svalero.agenda.modelo.Evento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ficheros {

    public final static String EVENTOS_DAT = "eventos.dat";

    public static void guardar(List<Evento> eventos, String nombreFichero) {
        ObjectOutputStream serializador = null;
        try {
            serializador = new ObjectOutputStream(new FileOutputStream(nombreFichero));
            serializador.writeObject(eventos);
        } catch (FileNotFoundException fnfe) {
            System.out.println("No se ha podido crear el fichero");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("No se ha podido escribir en el fichero");
        } finally {
            try {
                serializador.close();
            } catch (IOException ioe) {
                System.out.println("Se ha producido un error al guardar");
            }
        }
    }

    public static ArrayList<Evento> cargar(String nombreFichero) {
        ObjectInputStream deserializador = null;
        try {
            deserializador = new ObjectInputStream(new FileInputStream(nombreFichero));
            ArrayList<Evento> eventos = (ArrayList<Evento>) deserializador.readObject();
            deserializador.close();
            return eventos;
        } catch (FileNotFoundException fnfe) {
            System.out.println("El fichero tareas.dat no existe");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("El fichero no tiene el formato correcto");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("No se ha podido leer el fichero");
        }

        return null;
    }
}
