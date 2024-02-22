<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Group List Page</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container mt-3">
    <div class="row">
        <div class="col-md-12">
            <div class="d-flex justify-content-end mb-3">
                <a href="/student" class="btn btn-primary">Student Page</a>
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
                    <th scope="col">Name</th>
                    <th scope="col">Created At</th>
                    <th scope="col">Student Count</th>
                    <th scope="col"><a href="/group/add" class="btn btn-success">Add Group</a></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${groups}" var="group">
                    <tr>
                        <td><c:out value="${group.getId()}"/></td>
                        <td><c:out value="${group.getName()}"/></td>
                        <td><c:out value="${group.getCreatedAt()}"/></td>
                        <td><c:out value="${group.getStudentCount()}"/></td>
                        <td>
                            <a href="/group/update/${group.getId()}" class="btn btn-warning">Update</a> ||
                            <a href="/group/delete/${group.getId()}" class="btn btn-danger">Delete</a>
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
