package dev.zohidjon.project.servlets.group_servlets;

import dev.zohidjon.project.models.Groups;
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
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "GroupHomeServlet", value = "")
public class GroupHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Groups> query = entityManager.createQuery("SELECT g FROM Groups g order by g.id", Groups.class);
            List<Groups> groups = query.getResultList();
            entityManager.getTransaction().commit();

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            for (Groups group : groups) {
                Set<ConstraintViolation<Groups>> violations = validator.validate(group);
                if (!violations.isEmpty()) {
                    throw new ServletException("Validation error occurred for group: " + group.getId());
                }
            }

            request.setAttribute("groups", groups);
            request.getRequestDispatcher("/views/group/home.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
