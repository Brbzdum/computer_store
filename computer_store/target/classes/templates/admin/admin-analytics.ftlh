<!DOCTYPE html>
<html lang="ru">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
    <title>Аналитика</title>
</head>
<body>
<#include "../layout.ftlh">

<div>
    <h1>Аналитика</h1>
    <form action="/admin/revenue" method="get" class="row g-3 mb-4">
        <div class="col-md-6">
            <label for="startDate" class="form-label">Начало периода</label>
            <input type="datetime-local" class="form-control" id="startDate" name="startDate" required>
        </div>
        <div class="col-md-6">
            <label for="endDate" class="form-label">Конец периода</label>
            <input type="datetime-local" class="form-control" id="endDate" name="endDate" required>
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Сформировать отчет</button>
        </div>
    </form>
    <p><strong>Выручка:</strong> <span>${revenue! 0}</span> руб.</p>

    <section class="mt-5">
        <h2>Популярные товары</h2>
        <ul class="list-group mb-4">
            <#list popularProducts as product>
                <li class="list-group-item">${product.name! 'Товар'}</li>
            </#list>
        </ul>
    </section>

    <section class="mt-5">
        <h2>Популярные категории</h2>
        <ul class="list-group mb-4">
            <#list categoryPopularity as entry>
                <li class="list-group-item">${entry.key! 'Категория'} (${entry.value! 0})</li>
            </#list>
        </ul>
    </section>

    <section class="mt-5">
        <h2>Популярные производители</h2>
        <ul class="list-group mb-4">
            <#list manufacturerPopularity as entry>
                <li class="list-group-item">${entry.key! 'Производитель'} (${entry.value! 0})</li>
            </#list>
        </ul>
    </section>
</div>
</body>
</html>
