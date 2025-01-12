<div class="container mt-4">
    <h1 class="text-center mb-4">Профиль</h1>
    <dl class="row">
        <dt class="col-sm-3">Имя</dt>
        <dd class="col-sm-9">${user.name! 'Имя отсутствует'}</dd>

        <dt class="col-sm-3">Email</dt>
        <dd class="col-sm-9">${user.email! 'Email отсутствует'}</dd>

        <dt class="col-sm-3">Роли</dt>
        <dd class="col-sm-9">
            <#list user.roles as role>${role.name}</#list>
        </dd>
    </dl>
    <div class="text-center mt-4">
        <a href="/users/profile/purchases" class="btn btn-primary">Мои покупки</a>
        <form action="/logout" method="post" class="d-inline">
            <button type="submit" class="btn btn-danger">Выйти</button>
        </form>
    </div>
</div>
