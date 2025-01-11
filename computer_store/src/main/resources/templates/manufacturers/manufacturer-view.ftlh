<!DOCTYPE html>
<html>
<head>
    <title>Производитель: ${manufacturer.name! 'Производитель'}</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>${manufacturer.name! 'Название производителя'}</h1>
    <p>${manufacturer.description! 'Описание производителя'}</p>

    <h2>Продукция</h2>
    <!-- Форма для фильтрации по категориям -->
    <form method="get" action="/manufacturers/${manufacturer.id}" class="row g-3 mb-4">
        <div class="col-md-6">
            <label for="category" class="form-label">Фильтр по категории:</label>
            <select name="categoryId" id="category" class="form-select">
                <option value="" selected>Все категории</option>
                <#list categories as category>
                    <option value="${category.id! ''}">${category.name! ''}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-6 align-self-end">
            <button type="submit" class="btn btn-primary">Применить</button>
        </div>
    </form>

    <!-- Список продуктов -->
    <div class="row">
        <#list products as product>
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${product.mainImageUrl! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                    <div class="card-body">
                        <h5 class="card-title">${product.name! 'Название товара'}</h5>
                        <p class="card-text">Цена: ${product.price! '0'} ₽</p>
                        <p class="card-text">Категория: ${product.category.name! 'Категория'}</p>
                        <a href="/products/${product.id}" class="btn btn-primary">Подробнее</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>
