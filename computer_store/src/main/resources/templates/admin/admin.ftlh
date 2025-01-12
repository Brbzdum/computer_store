<div class="container mt-4">
    <h1 class="text-center mb-4">Административная панель</h1>

    <!-- Раздел Пользователи -->
    <section class="mb-5">
        <h2>Пользователи</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Роли</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.id! '1'}</td>
                    <td>${user.email! 'example@mail.com'}</td>
                    <td>
                        <#list user.roles as role>
                            ${role.name! ''}
                        </#list>
                    </td>
                    <td>${user.active?string("Активен", "Забанен")}</td>
                    <td>
                        <form action="/admin/user/ban/${user.id}" method="post" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
                            <button type="submit" class="btn btn-warning btn-sm">
                                ${user.active?string("Забанить", "Разбанить")}
                            </button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </section>

    <!-- Раздел Товары -->
    <section class="mb-5">
        <h2>Товары</h2>
        <a href="/admin/product/add" class="btn btn-success mb-3">Добавить товар</a>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Название</th>
                <th>Цена</th>
                <th>Категория</th>
                <th>Производитель</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list products as product>
                <tr>
                    <td>${product.name! 'Название'}</td>
                    <td>${product.price! '0'} ₽</td>
                    <td>${product.category.name! 'Категория'}</td>
                    <td>${product.manufacturer.name! 'Производитель'}</td>
                    <td>
                        <a href="/admin/product/edit/${product.id}" class="btn btn-primary btn-sm">Редактировать</a>
                        <form action="/admin/product/delete/${product.id}" method="post" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Вы уверены?')">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </section>
</div>
