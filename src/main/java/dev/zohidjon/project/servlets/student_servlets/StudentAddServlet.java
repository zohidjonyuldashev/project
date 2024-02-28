package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "StudentAddServlet", value = "/student/add")
public class StudentAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        int age = Integer.parseInt(request.getParameter("age"));
        Student student = Student.builder()
                .fullName(fullName)
                .groupID(groupID)
                .age(age)
                .build();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student);
            entityManager.getTransaction().commit();
            response.sendRedirect("/student");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            entityManager.close();
        }
    }
}
