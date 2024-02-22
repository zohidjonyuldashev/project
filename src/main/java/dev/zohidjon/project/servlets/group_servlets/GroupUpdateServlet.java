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

@WebServlet(name = "GroupUpdateServlet", urlPatterns = "/group/update/*")
public class GroupUpdateServlet extends HttpServlet {
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
                        .studentCount(resultSet.getInt("studentCount"))
                        .build();

                request.setAttribute("group", group);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/update.jsp");
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
        String name = request.getParameter("name");
        int studentCount = Integer.parseInt(request.getParameter("studentCount"));
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");
            PreparedStatement statement = connection.prepareStatement("update groups set name = ?, studentCount = ? where id = ?;");
            statement.setString(1, name);
            statement.setInt(2, studentCount);
            statement.setString(3, id);
            statement.execute();
            response.sendRedirect("/");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
