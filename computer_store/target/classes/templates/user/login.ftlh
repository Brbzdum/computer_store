<div class="container mt-4">
    <h1 class="text-center mb-4">Вход</h1>
    <!-- Блок для отображения ошибки -->
    <#if errorMessage??>
        <div class="alert alert-danger text-center">${errorMessage}</div>
    </#if>

    <form id="loginForm" action="/login" method="post" class="mx-auto" style="max-width: 400px;" novalidate>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
            <div class="invalid-feedback">Введите корректный email.</div>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль</label>
            <input type="password" id="password" name="password" class="form-control" required>
            <div class="invalid-feedback">Пароль обязателен.</div>
        </div>
        <button type="submit" class="btn btn-primary w-100">Войти</button>
    </form>
    <div class="text-center mt-3">
        <a href="/users/registration" class="text-decoration-none">Регистрация</a>
    </div>
</div>

<script>
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        const form = event.target;
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
        }
    });
</script>
