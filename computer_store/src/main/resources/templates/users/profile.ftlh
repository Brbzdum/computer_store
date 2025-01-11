<!DOCTYPE html>
<html>
<head>
    <title>Профиль пользователя</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>Ваш профиль</h1>
    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title">${user.name! 'Имя пользователя'}</h5>
            <p class="card-text"><strong>Email:</strong> ${user.email! 'email@example.com'}</p>
            <p class="card-text"><strong>Роли:</strong>
                <#list user.roles as role>
                    ${role.name! ''}
                </#list>
            </p>
            <a href="/users/profile/purchases" class="btn btn-primary">Мои покупки</a>
        </div>
    </div>
</div>
</body>
</html>
