package dev.zohidjon.project.servlets.student_servlets;

import dev.zohidjon.project.models.Groups;
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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "StudentAddServlet", value = "/student/add")
public class StudentAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String fullName = request.getParameter("fullName");
        String groupId = request.getParameter("groupId");
        int age = Integer.parseInt(request.getParameter("age"));

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Student student = new Student();
        student.setFullName(fullName);
        student.setAge(age);
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Student> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            throw new ServletException(errorMessage.toString());
        }


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orm_project");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Groups group = entityManager.find(Groups.class, groupId);
            if (group != null) {
                Student studentEntity = Student.builder()
                        .fullName(fullName)
                        .group(group)
                        .age(age)
                        .build();
                entityManager.persist(studentEntity);

                long studentCount = (long) entityManager.createQuery("SELECT COUNT(s) FROM Student s WHERE s.group.id = :groupId")
                        .setParameter("groupId", groupId)
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/student/create.jsp");
        dispatcher.forward(req, resp);
    }
}

