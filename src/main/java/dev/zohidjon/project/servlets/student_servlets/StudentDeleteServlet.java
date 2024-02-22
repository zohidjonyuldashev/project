package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Group;
import dev.zohidjon.project.models.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.Driver;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "StudentDeleteServlet", urlPatterns = "/student/delete/*")
public class StudentDeleteServlet extends HttpServlet {
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
            PreparedStatement statement = connection.prepareStatement("select * from student s where s.id = ?;");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Student student = Student.builder()
                        .id(resultSet.getString("id"))
                        .fullName(resultSet.getString("fullName"))
                        .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                        .groupID(resultSet.getString("groupID"))
                        .age(resultSet.getInt("age"))
                        .build();

                request.setAttribute("student", student);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/delete.jsp");
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
            PreparedStatement statement = connection.prepareStatement("delete from student s where s.id = ?");
            statement.setString(1, id);
            statement.execute();
            response.sendRedirect("/student");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
