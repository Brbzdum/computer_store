<#-- admin/manufacturers.ftlh -->
<div class="container mt-5">
    <h1 class="text-center mb-4">Управление Производителями</h1>
    <div class="mb-4 text-end">
        <a href="/admin/manufacturers/add" class="btn btn-success">
            <i class="bi bi-plus-circle-fill"></i> Добавить Производителя
        </a>
    </div>
    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Название</th>
            <th scope="col">Описание</th>
            <th scope="col" class="text-center">Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list manufacturers as manufacturer>
            <tr>
                <th scope="row">${manufacturer.id}</th>
                <td>${manufacturer.name! 'Название отсутствует'}</td>
                <td>${manufacturer.description! 'Описание отсутствует'}</td>
                <td class="text-center">
                    <a href="/admin/manufacturers/edit/${manufacturer.id}" class="btn btn-sm btn-primary me-2">
                        <i class="bi bi-pencil-square"></i> Редактировать
                    </a>
                    <form action="/admin/manufacturers/delete/${manufacturer.id}" method="post" class="d-inline">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Вы уверены, что хотите удалить этого производителя?');">
                            <i class="bi bi-trash-fill"></i> Удалить
                        </button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
