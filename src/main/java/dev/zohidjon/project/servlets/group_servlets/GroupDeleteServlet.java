package dev.zohidjon.project.servlets.group_servlets;

import dev.zohidjon.project.models.Groups;
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

@WebServlet(name = "GroupDeleteServlet", urlPatterns = "/group/delete/*")
public class GroupDeleteServlet extends HttpServlet {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Groups groups = entityManager.find(Groups.class, id);
            if (groups != null) {
                request.setAttribute("group", groups);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/delete.jsp");
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Groups groups = entityManager.find(Groups.class, id);
            if (groups != null) {
                entityManager.remove(groups);
            }
            entityManager.getTransaction().commit();
            response.sendRedirect("/");
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
