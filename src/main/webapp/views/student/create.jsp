<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Group Create</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container mt-5">
    <form method="post">
        <div class="mb-3">
            <label for="id" class="form-label">Id</label>
            <input type="number" class="form-control" id="id" name="id">
        </div>
        <div class="mb-3">
            <label for="fullName" class="form-label">Full Name</label>
            <input type="text" class="form-control" id="fullName" name="fullName">
        </div>
        <div class="mb-3">
            <label for="groupID" class="form-label">Group id</label>
            <input type="number" class="form-control" id="groupID" name="groupID">
        </div>
        <div class="mb-3">
            <label for="age" class="form-label">Age</label>
            <input type="number" class="form-control" id="age" name="age">
        </div>
        <button type="submit" class="btn btn-success">Save</button>
    </form>
</div>
<jsp:include page="/fragments/js.jsp"/>
</body>
</html>
