package dev.zohidjon.project.servlets.group_servlets;

import dev.zohidjon.project.models.Group;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.Driver;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "GroupDeleteServlet", urlPatterns = "/group/delete/*")
public class GroupDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");
            PreparedStatement statement = connection.prepareStatement("select * from groups g where g.id = ?;");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Group group = Group.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                        .studentCount(resultSet.getInt("studentCount"))
                        .build();

                request.setAttribute("group", group);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/delete.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");
            PreparedStatement statement = connection.prepareStatement("delete from groups g where g.id = ?");
            statement.setString(1, id);
            statement.execute();
            response.sendRedirect("/");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
