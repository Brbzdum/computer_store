<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<#include "layout.ftlh">

<div>
    <h1>Регистрация</h1>
    <form action="/users/registration" method="post" class="mt-4">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
        <div class="mb-3">
            <label for="name" class="form-label">Имя:</label>
            <input type="text" id="name" name="name" class="form-control" placeholder="Введите имя" required>
            <div class="text-danger">
                <#if fields.hasErrors('name')>
                    ${fields.errors('name')}
                </#if>
            </div>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" id="email" name="email" class="form-control" placeholder="Введите email" required>
            <div class="text-danger">
                <#if fields.hasErrors('email')>
                    ${fields.errors('email')}
                </#if>
            </div>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Пароль:</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Введите пароль" required>
            <div class="text-danger">
                <#if fields.hasErrors('password')>
                    ${fields.errors('password')}
                </#if>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>
    <p class="mt-3">Уже есть аккаунт? <a href="/login">Войдите</a>.</p>
    <#if errorMessage??>
        <div class="alert alert-danger mt-3" role="alert">
            <p>${errorMessage}</p>
        </div>
    </#if>
</div>
</body>
</html>
