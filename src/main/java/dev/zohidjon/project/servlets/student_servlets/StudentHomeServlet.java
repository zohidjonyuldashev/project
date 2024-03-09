package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@WebServlet(name = "StudentHomeServlet", value = "/student")
public class StudentHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s order by s.id", Student.class);
            List<Student> students = query.getResultList();
            entityManager.getTransaction().commit();

            Set<ConstraintViolation<List<Student>>> violations = validator.validate(students);
            if (!violations.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder();
                for (ConstraintViolation<List<Student>> violation : violations) {
                    errorMessage.append(violation.getMessage()).append("\n");
                }
                throw new ServletException(errorMessage.toString());
            }

            request.setAttribute("students", students);
            request.getRequestDispatcher("/views/student/home.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }
}
