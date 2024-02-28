package dev.zohidjon.project.servlets.group_servlets;

import dev.zohidjon.project.models.Groups;
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

@WebServlet(name = "GroupHomeServlet", value = "")
public class GroupHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Groups> groups;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Groups> query = entityManager.createQuery("select g from Groups g", Groups.class);
            groups = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            entityManager.close();
        }
        request.setAttribute("groups", groups);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/home.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
