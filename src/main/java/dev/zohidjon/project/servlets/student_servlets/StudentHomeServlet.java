package dev.zohidjon.project.servlets.student_servlets;

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
import java.util.ArrayList;

@WebServlet(name = "StudentHomeServlet", value = "/student")
public class StudentHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var students = new ArrayList<Student>();
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jakarta?currentSchema=jdbc",
                    "postgres",
                    "2004");
            PreparedStatement statement = connection.prepareStatement("select * from student;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(Student.builder()
                        .id(resultSet.getString("id"))
                        .fullName(resultSet.getString("fullName"))
                        .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                        .groupID(resultSet.getString("groupID"))
                        .age(resultSet.getInt("age"))
                        .build());
            }
            request.setAttribute("students", students);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/home.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
