<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"><title>Регистрация</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <%@ include file="../common/header.jspf" %>
        <main class="form-box">
            <h1>
                Регистрация
            </h1>
            <c:if test="${not empty error}">
            <p class="error">
                <c:out value="${error}"/>
            </p>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/register" class="validate-form">
                <label>Имя<input name="firstName" maxlength="100" required></label><label>Фамилия<input name="lastName" maxlength="100" required></label><label>Email<input type="email" name="email" maxlength="255" required></label><label>Пароль<input type="password" name="password" minlength="8" required></label><label>Повторите пароль<input type="password" name="confirmPassword" minlength="8" required></label><button type="submit">Создать аккаунт</button>
            </form>
        </main>
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
    </body>
</html>
