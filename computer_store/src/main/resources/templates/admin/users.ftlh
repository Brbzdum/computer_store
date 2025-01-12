<div class="container mt-4">
    <h1 class="text-center mb-4">Список пользователей</h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Email</th>
            <th>Роли</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>
                    <#list user.roles as role>
                        ${role.name}
                    </#list>
                </td>
                <td>${user.active?string("Активен", "Забанен")}</td>
                <td>
                    <form action="/admin/user/ban/${user.id}" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-warning btn-sm">${user.active?string("Забанить", "Разбанить")}</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
