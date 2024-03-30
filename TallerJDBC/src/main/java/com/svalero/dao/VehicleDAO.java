package com.svalero.dao;

import com.svalero.domain.Vehicle;
import com.svalero.util.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    private Connection connection;

    public VehicleDAO(Connection connection) {
        this.connection = connection;
    }

    public void registerVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicles (license_plate, brand, model, kilometers, registration_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicle.getLicensePlate());
        statement.setString(2, vehicle.getBrand());
        statement.setString(3, vehicle.getModel());
        statement.setInt(4, vehicle.getKilometers());
        statement.setDate(5, DateUtils.toDate(vehicle.getRegistrationDate()));
        statement.executeUpdate();
    }

    public boolean modifyVehicle(String licensePlate, Vehicle newVehicleData) throws SQLException {
        String sql = "UPDATE vehicles SET license_plate = ?, brand = ?, model = ?," +
                "kilometers = ? WHERE license_plate = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newVehicleData.getLicensePlate());
        statement.setString(2, newVehicleData.getBrand());
        statement.setString(3, newVehicleData.getModel());
        statement.setInt(4, newVehicleData.getKilometers());
        statement.setString(5, licensePlate);
        return statement.executeUpdate() != 0;
    }

    public boolean deleteVehicle(String licensePlate) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE license_plate = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, licensePlate);
        return statement.executeUpdate() != 0;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        String sql = "SELECT * FROM vehicles";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        return getList(resultSet);
    }

    public List<Vehicle> searchVehicles(String brand, String model) throws SQLException {
        String sql = "SELECT * FROM vehicles WHERE brand = ? AND model = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, brand);
        statement.setString(2, model);
        ResultSet resultSet = statement.executeQuery();

        return getList(resultSet);
    }

    public boolean isVehicle(String licensePlate) throws SQLException {
        String sql = "SELECT id FROM vehicles WHERE license_plate = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, licensePlate);
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    private List<Vehicle> getList(ResultSet resultSet) throws SQLException {
        List<Vehicle> vehicleList = new ArrayList<>();

        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setLicensePlate(resultSet.getString("license_plate"));
            vehicle.setBrand(resultSet.getString("brand"));
            vehicle.setModel(resultSet.getString("model"));
            vehicle.setKilometers(resultSet.getInt("kilometers"));
            vehicle.setRegistrationDate(DateUtils.toLocalDate(resultSet.getDate("registration_date")));
            vehicleList.add(vehicle);
        }

        return vehicleList;
    }
}
