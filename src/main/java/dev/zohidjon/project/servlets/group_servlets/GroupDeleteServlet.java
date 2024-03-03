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

@WebServlet(name = "GroupDeleteServlet", urlPatterns = "/group/delete/*")
public class GroupDeleteServlet extends HttpServlet {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("orm_project");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Groups group = em.find(Groups.class, Integer.parseInt(id));
            if (group != null) {
                request.setAttribute("group", group);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/group/delete.jsp");
                dispatcher.forward(request, response);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException(e);
        } finally {
            em.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(1);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Groups group = em.find(Groups.class, Integer.parseInt(id));
            if (group != null) {
                em.remove(group);
            }
            em.getTransaction().commit();
            response.sendRedirect("/");
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
