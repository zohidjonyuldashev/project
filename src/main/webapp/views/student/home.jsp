<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Student List Page</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>

<div class="container mt-3">
    <div class="row">
        <div class="col-md-12">
            <div class="d-flex justify-content-start mb-3">
                <a href="/" class="btn btn-primary">Group Page</a>
            </div>
        </div>
    </div>
</div>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-10 offset-1">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Created At</th>
                    <th scope="col">Group Id</th>
                    <th scope="col">Age</th>
                    <th scope="col"><a href="/student/add" class="btn btn-success">Add Student</a></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${students}" var="student">
                    <tr>
                        <td><c:out value="${student.getId()}"/></td>
                        <td><c:out value="${student.getFullName()}"/></td>
                        <td><c:out value="${student.getCreatedAt()}"/></td>
                        <td><c:out value="${student.getGroup().getId()}"/></td>
                        <td><c:out value="${student.getAge()}"/></td>
                        <td>
                            <a href="/student/update/${student.getId()}" class="btn btn-warning">Update</a> ||
                            <a href="/student/delete/${student.getId()}" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/fragments/js.jsp"/>
</body>
</html>