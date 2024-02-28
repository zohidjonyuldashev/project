<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Student delete</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container mt-5">
    <form method="post">
        <div class="alert alert-danger">
            <h1>Are you sure you want to delete the "<i><c:out value="${student.getFullName()}"/></i>" student in the
                "<i><c:out value="${student.getGroupID()}"/></i>" groups ?</h1>
        </div>
        <a href="/" class="btn btn-warning">Back</a>
        <button type="submit" class="btn btn-danger">Yes</button>
    </form>
</div>
<jsp:include page="/fragments/js.jsp"/>
</body>
</html>
