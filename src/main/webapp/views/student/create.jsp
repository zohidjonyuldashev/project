<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Student Create</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container mt-5">
    <form method="post">
        <div class="mb-3">
            <label for="fullName" class="form-label">Full Name</label>
            <input type="text" class="form-control" id="fullName" name="fullName">
        </div>
        <div class="mb-3">
            <label for="groupId" class="form-label">Group id</label>
            <input type="number" class="form-control" id="groupId" name="groupId">
        </div>
        <div class="mb-3">
            <label for="age" class="form-label">Age</label>
            <input type="number" class="form-control" id="age" name="age">
        </div>
        <a href="/student" class="btn btn-warning">Back</a>
        <button type="submit" class="btn btn-success">Save</button>
    </form>
</div>
<jsp:include page="/fragments/js.jsp"/>
</body>
</html>
