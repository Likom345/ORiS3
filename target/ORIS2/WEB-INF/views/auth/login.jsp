<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="../common/header.jspf" %>

    <main class="form-box">
        <h1>Вход</h1>

        <%-- Сообщения передаются сервлетом --%>
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}"/></p>
        </c:if>

        <c:if test="${param.registered == '1'}">
            <p class="success">Регистрация прошла успешно. Войдите в аккаунт.</p>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <label>
                Email
                <input type="email" name="email" required>
            </label>
            <label>
                Пароль
                <input type="password" name="password" required>
            </label>
            <button type="submit">Войти</button>
        </form>

        <p>Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрироваться</a></p>
    </main>
</body>
</html>
