package com.svalero.servlet;

import com.svalero.dao.Database;
import com.svalero.dao.VehicleDAO;
import com.svalero.util.DateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/edit-vehicle")
@MultipartConfig
public class AddEditVehicleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String imagePath = request.getServletContext().getInitParameter("image-path");
        String licensePlate = request.getParameter("licensePlate");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int kilometers = Integer.parseInt(request.getParameter("kilometers"));

        int id = 0;
        String action = request.getParameter("action");
        if (action.equals("edit")) {
            id = Integer.parseInt(request.getParameter("id"));
        }

        try {
            // Subir la imagen a la carpeta taller_data
            Part imagePart = request.getPart("image");
            String fileName;
            if (imagePart.getSize() == 0) {
                fileName = "no_image.jpg";
            } else {
                fileName = UUID.randomUUID() + ".jpg";
                InputStream fileStream = imagePart.getInputStream();
                Files.copy(fileStream, Path.of(imagePath + File.separator + fileName));
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database.connect();

            if (action.equals("edit")) {
                int vehicleId = id;
                Database.jdbi.withExtension(VehicleDAO.class, dao -> {
                    dao.editVehicle(licensePlate, brand, model, kilometers, fileName, vehicleId);
                    return null;
                });
            } else {
                Database.jdbi.withExtension(VehicleDAO.class, dao -> {
                    dao.addVehicle(licensePlate, brand, model, kilometers, fileName, DateUtils.getDate(LocalDate.now()));
                    return null;
                });
            }
            // TODO Mientras no corregimos el javascript para envio asincrono podemos redirigir al index
            response.sendRedirect("index.jsp");

            out.println("<div class='alert alert-success' role='alert'>Vehiculo guardado correctamente</div>");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
