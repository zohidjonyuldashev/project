<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Group Update</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container mt-5">
    <form method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Group Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${group.getName()}">
        </div>
        <div class="mb-3">
            <label for="studentCount" class="form-label">Student Count</label>
            <input type="number" class="form-control" id="studentCount" name="studentCount"
                   value="${group.getStudentCount()}">
        </div>
        <a href="/" class="btn btn-warning">Back</a>
        <button type="submit" class="btn btn-success">Update</button>
    </form>
</div>
<jsp:include page="/fragments/js.jsp"/>
</body>
</html>
