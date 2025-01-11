<!DOCTYPE html>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1 class="text-center">Добро пожаловать в Магазин Компьютеров</h1>
    <section class="mt-5">
        <h2>Категории</h2>
        <div class="row">
            <#list categories as category>
                <div class="col-md-3 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${category.name! 'Категория'}</h5>
                            <p class="card-text">${category.description! 'Описание'}</p>
                            <a href="/categories/${category.id}" class="btn btn-primary">Просмотреть</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </section>

    <section class="mt-5">
        <h2>Популярные Производители</h2>
        <div class="row">
            <#list manufacturers as manufacturer>
                <div class="col-md-3 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${manufacturer.name! 'Производитель'}</h5>
                            <p class="card-text">${manufacturer.description! 'Описание'}</p>
                            <a href="/manufacturers/${manufacturer.id}" class="btn btn-primary">Просмотреть</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </section>
</div>
</body>
</html>
