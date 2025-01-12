<div class="container mt-4">
    <h1 class="text-center mb-4">Регистрация</h1>
    <form action="/users/registration" method="post" class="mx-auto" style="max-width: 400px;">
        <div class="mb-3">
            <label for="name" class="form-label">Имя</label>
            <input type="text" id="name" name="name" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-success w-100">Зарегистрироваться</button>
    </form>
    <div class="text-center mt-3">
        <a href="/users/login" class="text-decoration-none">Уже зарегистрированы? Войдите</a>
    </div>
</div>
