<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"><title>Мои заказы</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <%@ include file="../common/header.jspf" %>
        <main class="container">
            <h1>
                Мои заказы
            </h1>
            <c:if test="${not empty param.created}">
            <p class="success">
                Заказ №${param.created} успешно создан.
            </p>
            </c:if><c:forEach var="o" items="${orders}">
            <div class="cart-row">
                <strong>Заказ №${o.orderId}</strong><span><c:out value="${o.orderDate}"/></span><span class="status"><c:out value="${o.status}"/></span><span><c:out value="${o.comment}"/></span>
            </div>
            </c:forEach><c:if test="${empty orders}">
            <p>
                У вас пока нет заказов.
            </p>
            </c:if>
        </main>
    </body>
</html>
