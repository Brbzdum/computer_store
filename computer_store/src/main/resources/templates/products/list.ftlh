<!DOCTYPE html>
<html>
<head>
    <title>Список товаров</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>Список товаров</h1>
    <!-- Форма для фильтрации -->
    <form method="get" action="/products" class="row g-3 mb-4">
        <div class="col-md-4">
            <label for="category" class="form-label">Категория:</label>
            <select name="categoryId" id="category" class="form-select">
                <option value="" selected>Все категории</option>
                <#list categories as category>
                    <option value="${category.id! ''}">${category.name! ''}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-4">
            <label for="manufacturer" class="form-label">Производитель:</label>
            <select name="manufacturerId" id="manufacturer" class="form-select">
                <option value="" selected>Все производители</option>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id! ''}">${manufacturer.name! ''}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-4 align-self-end">
            <button type="submit" class="btn btn-primary">Фильтровать</button>
        </div>
    </form>

    <!-- Список товаров -->
    <div class="row">
        <#list products as product>
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${product.mainImageUrl! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                    <div class="card-body">
                        <h5 class="card-title">${product.name! 'Название товара'}</h5>
                        <p class="card-text">Цена: ${product.price! '0'} ₽</p>
                        <a href="/products/${product.id}" class="btn btn-primary">Подробнее</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>
