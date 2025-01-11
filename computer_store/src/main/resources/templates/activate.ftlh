<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Активация аккаунта</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>Активация аккаунта</h1>
    <p>${message! 'Результат активации'}</p>
    <a href="/login" class="btn btn-primary">Перейти на страницу входа</a>
</div>
</body>
</html>
