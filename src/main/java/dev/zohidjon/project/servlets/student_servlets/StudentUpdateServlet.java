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

@WebServlet(name = "StudentUpdateServlet", urlPatterns = "/student/update/*")
public class StudentUpdateServlet extends HttpServlet {
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/update.jsp");
                dispatcher.forward(request, response);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);
        String fullName = request.getParameter("fullName");
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        int age = Integer.parseInt(request.getParameter("age"));
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                student.setFullName(fullName);
                student.setGroupID(groupID);
                student.setAge(age);
            }
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
