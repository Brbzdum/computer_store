<div class="container mt-4">
    <h1 class="text-center mb-4">Вход</h1>
    <form action="/login" method="post" class="mx-auto" style="max-width: 400px;">
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Войти</button>
    </form>
    <div class="text-center mt-3">
        <a href="/users/registration" class="text-decoration-none">Регистрация</a>
    </div>
</div>
