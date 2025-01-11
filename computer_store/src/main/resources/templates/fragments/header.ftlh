<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title!''}</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Компьютерный магазин</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Переключить навигацию">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/products">Товары</a>
                </li>
                <#if user?has_content && user.roles?seq_contains("ROLE_ADMIN")>
                    <li class="nav-item"><a class="nav-link" href="/admin">Админ Панель</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/users">Пользователи</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/products">Управление Товарами</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/categories">Категории</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/manufacturers">Производители</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/sales">Продажи</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/analytics">Аналитика</a></li>
                </#if>
            </ul>
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <#if user?has_content>
                    <li class="nav-item"><a class="nav-link" href="/users/profile">Профиль</a></li>
                    <li class="nav-item">
                        <form action="/logout" method="post" class="d-inline">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-link nav-link" style="display: inline; cursor: pointer;">Выйти</button>
                        </form>
                    </li>
                <#else>
                    <li class="nav-item"><a class="nav-link" href="/login">Войти</a></li>
                    <li class="nav-item"><a class="nav-link" href="/registration">Регистрация</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
