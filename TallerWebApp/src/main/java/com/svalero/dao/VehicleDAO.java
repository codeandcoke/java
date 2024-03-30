package com.svalero.dao;

import com.svalero.domain.Vehicle;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.sql.Date;
import java.util.List;

public interface VehicleDAO {

    @SqlQuery("SELECT * FROM vehicles")
    @UseRowMapper(VehicleMapper.class)
    List<Vehicle> getVehicles();

    @SqlQuery("SELECT * FROM vehicles WHERE id = ?")
    @UseRowMapper(VehicleMapper.class)
    Vehicle getVehicle(int id);

    @SqlUpdate("INSERT INTO vehicles (license_plate, brand, model, kilometers, image, registration_date) VALUES (?, ?, ?, ?, ?, ?)")
    void addVehicle(String licensePlate, String brand, String model, int kilometers, String image, Date registrationDate);

    @SqlUpdate("DELETE FROM vehicles WHERE id = ?")
    void removeVehicle(int id);

    @SqlUpdate("UPDATE vehicles SET license_plate = ?, brand = ?, model = ?, kilometers = ?, image = ? WHERE id = ?")
    void editVehicle(String licensePlate, String brand, String model, int kilometers, String image, int id);

    @SqlUpdate("UPDATE vehicles SET license_plate = ?, brand = ?, model = ?, kilometers = ? WHERE id = ?")
    void editVehicle(String licensePlate, String brand, String model, int kilometers, int id);
}
