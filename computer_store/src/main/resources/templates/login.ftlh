<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<#include "layout.ftlh">

<div>
    <h1>Вход в систему</h1>
    <form action="/login" method="post" class="mt-4">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" id="email" name="username" class="form-control" placeholder="Введите email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль:</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Введите пароль" required>
        </div>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
    <p class="mt-3">Нет аккаунта? <a href="/registration">Зарегистрируйтесь</a>.</p>
    <#if param.error??>
        <div class="alert alert-danger mt-3" role="alert">
            <p>Неверный email или пароль.</p>
        </div>
    </#if>
</div>
</body>
</html>
