package dev.zohidjon.project.servlets.group_servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "GroupAddServlet", value = "/group/add")
public class GroupAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        int studentCount = Integer.parseInt(request.getParameter("studentCount"));
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");

            PreparedStatement statement = connection.prepareStatement("INSERT INTO groups(id, name, studentCount) VALUES (?, ?, ?);");
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setInt(3, studentCount);
            statement.execute();
            response.sendRedirect("/");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
