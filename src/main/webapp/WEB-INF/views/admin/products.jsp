<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8"><title>Управление товарами</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <%@ include file="../common/header.jspf" %>
        <main class="container">
            <h1>
                Администрирование товаров
            </h1>
            <p>
                <a href="${pageContext.request.contextPath}/admin/orders">Перейти к заказам</a>
            </p>
            <c:if test="${not empty error}">
            <p class="error">
                <c:out value="${error}"/>
            </p>
            </c:if>
            <section class="admin-form">
                <h2>
                    Добавить товар
                </h2>
                <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/products" class="validate-form">
                    <input type="hidden" name="action" value="save"><label>Название<input name="name" required maxlength="150"></label><label>Категория<select name="category"><option>Хлеб</option><option>Выпечка</option><option>Торт</option><option>Пирожное</option><option>Другое</option></select></label><label>Описание<textarea name="description"></textarea></label><label>Цена<input type="number" name="price" min="0" step="0.01" required></label><label>Фотография<input type="file" name="image" accept="image/png,image/jpeg,image/webp"></label><label class="check"><input type="checkbox" name="available" checked>Доступен</label><button>Добавить</button>
                </form>
            </section>
            <h2>
                Существующие товары
            </h2>
            <c:forEach var="p" items="${products}">
            <section class="admin-form">
                <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/products" class="validate-form">
                    <input type="hidden" name="id" value="${p.productId}"><input type="hidden" name="oldImage" value="${fn:escapeXml(p.imagePath)}"><label>Название<input name="name" value="<c:out value='${p.name}'/>" required></label><label>Категория<select name="category"><option value="Хлеб" ${p.category == 'Хлеб' ? 'selected' : ''}>Хлеб</option><option value="Выпечка" ${p.category == 'Выпечка' ? 'selected' : ''}>Выпечка</option><option value="Торт" ${p.category == 'Торт' ? 'selected' : ''}>Торт</option><option value="Пирожное" ${p.category == 'Пирожное' ? 'selected' : ''}>Пирожное</option><option value="Другое" ${p.category == 'Другое' ? 'selected' : ''}>Другое</option></select></label><label>Описание<textarea name="description"><c:out value="${p.description}"/></textarea></label><label>Цена<input type="number" name="price" min="0" step="0.01" value="${p.salePrice}" required></label><label>Заменить фото<input type="file" name="image" accept="image/png,image/jpeg,image/webp"></label><label class="check"><input type="checkbox" name="available" ${p.available ? 'checked' : ''}>Доступен</label><button>Сохранить изменения</button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/admin/products" onsubmit="return confirm('Удалить товар?')">
                    <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${p.productId}"><button class="danger">Удалить</button>
                </form>
            </section>
            </c:forEach>
        </main>
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
    </body>
</html>
