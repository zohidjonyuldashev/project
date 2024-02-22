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
import java.util.ArrayList;

@WebServlet(name = "GroupHomeServlet", value = "")
public class GroupHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var groups = new ArrayList<Group>();
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");
            PreparedStatement statement = connection.prepareStatement("select * from groups");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(Group.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                        .studentCount(resultSet.getInt("studentCount"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("groups", groups);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/home.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
