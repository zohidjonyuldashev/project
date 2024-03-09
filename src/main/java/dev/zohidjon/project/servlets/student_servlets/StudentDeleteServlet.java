package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Group;
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

@WebServlet(name = "StudentDeleteServlet", urlPatterns = "/student/delete/*")
public class StudentDeleteServlet extends HttpServlet {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("orm_project");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, Integer.parseInt(id));
            if (student != null) {
                request.setAttribute("student", student);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student/delete.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, Integer.parseInt(id));
            if (student != null) {
                Group group = student.getGroup();
                entityManager.remove(student);

                long studentCount = (long) entityManager.createQuery("SELECT COUNT(s) FROM Student s WHERE s.group.id = :groupId")
                        .setParameter("groupId", group.getId())
                        .getSingleResult();
                group.setStudentCount((int) studentCount);
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
