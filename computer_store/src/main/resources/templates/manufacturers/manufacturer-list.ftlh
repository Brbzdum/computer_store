<!DOCTYPE html>
<html>
<head>
    <title>Производители</title>
</head>
<body>
<div>
    <h1>Производители</h1>
    <table>
        <thead>
        <tr>
            <th>Название</th>
            <th>Описание</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list manufacturers as manufacturer>
            <tr>
                <td>${manufacturer.name! ''}</td>
                <td>${manufacturer.description! ''}</td>
                <td>
                    <a href="/admin/manufacturers/edit/${manufacturer.id}">Редактировать</a> |
                    <form action="/admin/manufacturers/delete/${manufacturer.id}" method="post" style="display:inline;">
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <a href="/admin/manufacturers/add">Добавить производителя</a>
</div>
</body>
</html>
