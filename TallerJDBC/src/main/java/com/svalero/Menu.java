package com.svalero;

import com.svalero.dao.Database;
import com.svalero.dao.VehicleDAO;
import com.svalero.domain.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;

    public Menu() {
        keyboard = new Scanner(System.in);

        database = new Database();
        connection = database.getConnection();
    }

    public void show() {
        boolean salir = false;

        do {
            System.out.println("Gestaller v0.1");
            System.out.println("1. Registrar un vehiculo");
            System.out.println("2. Modificar un vehiculo");
            System.out.println("3. Buscar un vehiculo");
            System.out.println("4. Dar de baja un vehiculo");
            System.out.println("5. Listar todos los vehiculos");
            System.out.println("s. Salir");
            String choice = keyboard.nextLine();
            switch (choice) {
                case "1" -> registerVehicle();
                case "2" -> modifyVehicle();
                case "3" -> searchVehicle();
                case "4" -> deleteVehicle();
                case "5" -> listAllVehicles();
                case "s" -> {
                    salir = true;
                    database.closeConnection();
                }
            }

        } while (!salir);

        System.out.println("Fin");
    }

    private void registerVehicle() {
        System.out.print("Matricula: ");
        String licensePlate = keyboard.nextLine();
        System.out.print("Marca: ");
        String brand = keyboard.nextLine();
        System.out.print("Modelo: ");
        String model = keyboard.nextLine();
        System.out.print("Kilómetros: ");
        int kilometers = Integer.parseInt(keyboard.nextLine());

        Vehicle vehicle = new Vehicle(licensePlate, brand, model, kilometers, LocalDate.now());
        VehicleDAO vehicleDAO = new VehicleDAO(connection);
        try {
            vehicleDAO.registerVehicle(vehicle);
            System.out.println("Vehiculo registrado correctamente");
        } catch (SQLException sqle) {
            System.out.println("Fallo de conexión con la base de datos");
            sqle.printStackTrace();
        }
    }

    private void modifyVehicle() {
        System.out.print("Introduce la matricula del vehiculo que quieres modificar:");
        String previousLicensePlate = keyboard.nextLine();
        VehicleDAO vehicleDAO = new VehicleDAO(connection);
        try {
            if (!vehicleDAO.isVehicle(previousLicensePlate)) {
                System.out.println("El vehículo no existe");
                return;
            }

            System.out.print("Nueva matricula: ");
            String licensePlate = keyboard.nextLine();
            System.out.print("Nueva marca: ");
            String brand = keyboard.nextLine();
            System.out.print("Nuevo modelo: ");
            String model = keyboard.nextLine();
            System.out.print("Actualizar kilómetros: ");
            int kilometers = Integer.parseInt(keyboard.nextLine());

            Vehicle vehicle = new Vehicle(licensePlate, brand, model, kilometers);

            boolean modified = vehicleDAO.modifyVehicle(previousLicensePlate, vehicle);
            if (modified)
                System.out.println("Vehiculo modificado correctamente");
            else
                System.out.println("No se ha podido modificar el vehículo");
            System.out.println("Vehiculo modificado correctamente");
        } catch (SQLException sqle) {
            System.out.println("Fallo de conexión con la base de datos");
            sqle.printStackTrace();
        }
    }

    private void searchVehicle() {
        System.out.print("Marca: ");
        String brand = keyboard.nextLine();
        System.out.print("Modelo: ");
        String model = keyboard.nextLine();

        VehicleDAO vehicleDAO = new VehicleDAO(connection);
        try {
            List<Vehicle> vehicles = vehicleDAO.searchVehicles(brand, model);
            vehicles.forEach(System.out::println);
        } catch (SQLException sqle) {
            System.out.println("Fallo de conexión con la base de datos");
            sqle.printStackTrace();
        }
    }

    private void deleteVehicle() {
        System.out.print("Matricula: ");
        String licensePlate = keyboard.nextLine();

        VehicleDAO vehicleDAO = new VehicleDAO(connection);
        try {
            boolean deleted = vehicleDAO.deleteVehicle(licensePlate);
            if (deleted)
                System.out.println("Vehiculo dado de baja correctamente");
            else
                System.out.println("Vehiculo no encontrado");
        } catch (SQLException sqle) {
            System.out.println("Fallo de conexión con la base de datos");
            sqle.printStackTrace();
        }
    }

    private void listAllVehicles() {
        VehicleDAO vehicleDAO = new VehicleDAO(connection);
        try {
            List<Vehicle> vehicleList = vehicleDAO.getAllVehicles();
            vehicleList.forEach(System.out::println);
        } catch (SQLException sqle) {
            System.out.println("Fallo de conexión con la base de datos");
            sqle.printStackTrace();
        }
    }
}
