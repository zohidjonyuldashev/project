package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Groups;
import dev.zohidjon.project.models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "StudentUpdateServlet", urlPatterns = "/student/update/*")
public class StudentUpdateServlet extends HttpServlet {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, Integer.parseInt(id));
            if (student != null) {
                request.setAttribute("student", student);
                request.getRequestDispatcher("/views/student/update.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);
        String fullName = request.getParameter("fullName");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        int age = Integer.parseInt(request.getParameter("age"));

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, Integer.parseInt(id));
            if (student != null) {
                student.setFullName(fullName);
                Groups group = em.find(Groups.class, groupId);
                student.setGroup(group);
                student.setAge(age);
            }
            em.getTransaction().commit();
            response.sendRedirect("/student");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            em.close();
        }
    }
}
