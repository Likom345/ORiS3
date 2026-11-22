<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Пекарня — каталог</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <%@ include file="common/header.jspf" %>
        <main class="container">
            <section class="intro">
                <h1>
                    Свежая выпечка каждый день
                </h1>
                <p>
                    Выбирайте любимые изделия нашей пекарни.
                </p>
            </section>
            <h2>
                Каталог
            </h2>
            <div class="products">
                <c:forEach var="p" items="${products}">
                <article class="product">
                    <c:if test="${p.hasImage}"><img src="${pageContext.request.contextPath}/product-image?id=${p.productId}" alt="Изображение товара"></c:if><c:if test="${not p.hasImage}">
                    <div class="placeholder">
                        Нет фото
                    </div>
                    </c:if>
                    <div class="product-body">
                        <span class="category"><c:out value="${p.category}"/></span>
                        <h3>
                            <c:out value="${p.name}"/>
                        </h3>
                        <p>
                            <c:out value="${p.description}"/>
                        </p>
                        <strong><c:out value="${p.salePrice}"/> ₽</strong><c:if test="${p.available}">
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="id" value="${p.productId}"><input type="hidden" name="quantity" value="1"><button>В корзину</button>
                        </form>
                        </c:if><c:if test="${not p.available}"><span class="muted">Нет в наличии</span></c:if>
                    </div>
                </article>
                </c:forEach>
            </div>
            <c:if test="${empty products}">
            <p>
                Каталог пока пуст. Администратор может добавить товары.
            </p>
            </c:if>
        </main>
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
    </body>
</html>
