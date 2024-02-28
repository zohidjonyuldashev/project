package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentHomeServlet", value = "/student")
public class StudentHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> students;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
            students = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            entityManager.close();
        }
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/home.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
