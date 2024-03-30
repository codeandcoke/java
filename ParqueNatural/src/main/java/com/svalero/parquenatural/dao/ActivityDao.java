package com.svalero.parquenatural.dao;

import com.svalero.parquenatural.domain.Activity;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.sql.Date;
import java.util.List;

public interface ActivityDao {

    @SqlQuery("SELECT * FROM activities")
    @UseRowMapper(ActivityMapper.class)
    List<Activity> getAllActivities();

    @SqlQuery("SELECT * FROM activities WHERE id = ?")
    @UseRowMapper(ActivityMapper.class)
    Activity getActivity(int id);

    @SqlUpdate("INSERT INTO activities (name, description, datetime, price, picture) VALUES (?, ?, ?, ?, ?)")
    int addActivity(String name, String description, Date date, float price, String picture);

    @SqlUpdate("UPDATE activities SET name = ?, description = ?, datetime = ?, price = ?, picture = ? WHERE id = ?")
    int updateActivity(String name, String description, Date date, float price, String picture, int id);

    @SqlUpdate("DELETE FROM activities WHERE id = ?")
    int removeActivity(int id);
}
