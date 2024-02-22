package dev.zohidjon.project.servlets.student_servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.Driver;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "StudentAddServlet", value = "/student/add")
public class StudentAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String fullName = request.getParameter("fullName");
        String groupID = request.getParameter("groupID");
        int age = Integer.parseInt(request.getParameter("age"));

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");

            PreparedStatement checkGroupStatement = connection.prepareStatement("SELECT id FROM groups WHERE id = ?");
            checkGroupStatement.setString(1, groupID);
            ResultSet groupResultSet = checkGroupStatement.executeQuery();

            if (!groupResultSet.next()) {
                throw new IllegalArgumentException("Invalid groupID: " + groupID);
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO student(id, fullName, groupID, age) VALUES (?, ?, ?, ?);");
            statement.setString(1, id);
            statement.setString(2, fullName);
            statement.setString(3, groupID);
            statement.setInt(4, age);
            statement.execute();
            response.sendRedirect("/student");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
