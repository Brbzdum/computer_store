<div class="container mt-4">
    <h1 class="text-center mb-4">Регистрация</h1>
    <form id="registrationForm" action="/users/registration" method="post" class="mx-auto" style="max-width: 400px;" novalidate>
        <div class="mb-3">
            <label for="name" class="form-label">Имя</label>
            <input type="text" id="name" name="name" class="form-control" required minlength="2" maxlength="50">
            <div class="invalid-feedback">Имя должно содержать от 2 до 50 символов.</div>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
            <div class="invalid-feedback">Введите корректный email.</div>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль</label>
            <input type="password" id="password" name="password" class="form-control" required minlength="6">
            <div class="invalid-feedback">Пароль должен содержать минимум 6 символов.</div>
        </div>
        <button type="submit" class="btn btn-success w-100">Зарегистрироваться</button>
    </form>
    <div class="text-center mt-3">
        <a href="/users/login" class="text-decoration-none">Уже зарегистрированы? Войдите</a>
    </div>
</div>

<script>
    document.getElementById('registrationForm').addEventListener('submit', function(event) {
        const form = event.target;
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
        }
    });
</script>


