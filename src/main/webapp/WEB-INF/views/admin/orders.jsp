<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"><title>Управление заказами</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body data-context="${pageContext.request.contextPath}">
        <%@ include file="../common/header.jspf" %>
        <main class="container">
            <h1>
                Заказы покупателей
            </h1>
            <p>
                <a href="${pageContext.request.contextPath}/admin/products">Управление товарами</a>
            </p>
            <c:forEach var="o" items="${orders}">
            <section class="cart-row" id="order-${o.orderId}">
                <div>
                    <strong>Заказ №${o.orderId}</strong>
                    <div>
                        <c:out value="${o.orderDate}"/>· Покупатель №${o.customerId}
                    </div>
                    <div>
                        <c:out value="${o.comment}"/>
                    </div>
                    <span class="status"><c:out value="${o.status}"/></span>
                </div>
                <form class="ajax-order-form" method="post" action="${pageContext.request.contextPath}/admin/ajax/order-status">
                    <input type="hidden" name="id" value="${o.orderId}"><select name="status"><option value="Новый" ${o.status == 'Новый' ? 'selected' : ''}>Новый</option><option value="В работе" ${o.status == 'В работе' ? 'selected' : ''}>В работе</option><option value="Готов" ${o.status == 'Готов' ? 'selected' : ''}>Готов</option><option value="Выдан" ${o.status == 'Выдан' ? 'selected' : ''}>Выдан</option><option value="Отменён" ${o.status == 'Отменён' ? 'selected' : ''}>Отменён</option></select><button>Изменить статус</button><span class="ajax-message" aria-live="polite"></span>
                </form>
            </section>
            </c:forEach><c:if test="${empty orders}">
            <p>
                Заказов пока нет.
            </p>
            </c:if>
        </main>
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
    </body>
</html>
