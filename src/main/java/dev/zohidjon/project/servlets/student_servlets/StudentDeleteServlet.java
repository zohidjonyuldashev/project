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

@WebServlet(name = "StudentDeleteServlet", urlPatterns = "/student/delete/*")
public class StudentDeleteServlet extends HttpServlet {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                request.setAttribute("student", student);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/delete.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException();
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
            entityManager.getTransaction().commit();
            response.sendRedirect("/student");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException();
        } finally {
            entityManager.close();
        }
    }
}
