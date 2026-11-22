// Клиентская проверка форм и защита от повторного нажатия кнопки.
document.querySelectorAll('.validate-form').forEach(function (form) {
    form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
            event.preventDefault();
            form.reportValidity();
            return;
        }
        const button = form.querySelector('button[type="submit"], button:not([type])');
        if (button) {
            button.disabled = true;
            button.textContent = 'Сохранение...';
        }
    });
});

// AJAX: обновляем статус заказа без перезагрузки всей страницы.
document.querySelectorAll('.ajax-order-form').forEach(function (form) {
    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        const button = form.querySelector('button');
        const message = form.querySelector('.ajax-message');
        button.disabled = true;
        message.textContent = 'Обновление...';
        try {
            const response = await fetch(form.action, { method: 'POST', body: new FormData(form) });
            const result = await response.json();
            if (!response.ok) throw new Error(result.message);
            message.textContent = result.message;
            const status = form.querySelector('select').value;
            form.closest('.cart-row').querySelector('.status').textContent = status;
        } catch (error) {
            message.textContent = error.message || 'Ошибка запроса';
        } finally {
            button.disabled = false;
        }
    });
});
