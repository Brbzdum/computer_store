<div class="container mt-4">
    <h1 class="text-center mb-4">Производители</h1>
    <table class="table table-striped">
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
                <td>${manufacturer.name! 'Название отсутствует'}</td>
                <td>${manufacturer.description! 'Описание отсутствует'}</td>
                <td>
                    <a href="/admin/manufacturers/edit/${manufacturer.id}" class="btn btn-sm btn-primary">Редактировать</a>
                    <form action="/admin/manufacturers/delete/${manufacturer.id}" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Вы уверены, что хотите удалить этого производителя?');">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <div class="text-center mt-4">
        <a href="/admin/manufacturers/add" class="btn btn-success">Добавить производителя</a>
    </div>
</div>
