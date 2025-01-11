<!DOCTYPE html>
<html>
<head>
    <title>Список товаров</title>
    <link rel="stylesheet" href="/static/css/styles.css"> <!-- Подключение стилей -->
</head>
<body>
<#include "../layout.ftlh">

<div class="container mt-4">
    <h1 class="text-center mb-4">Список товаров</h1>

    <!-- Форма для фильтрации -->
    <form method="get" action="/products" class="row g-3 mb-4">
        <div class="col-md-4">
            <label for="category" class="form-label">Категория:</label>
            <select name="categoryId" id="category" class="form-select">
                <option value="" <#if selectedCategoryId?? && selectedCategoryId == ''>selected</#if>>Все категории</option>
                <#list categories as category>
                    <option value="${category.id}" <#if selectedCategoryId == category.id>selected</#if>>${category.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-4">
            <label for="manufacturer" class="form-label">Производитель:</label>
            <select name="manufacturerId" id="manufacturer" class="form-select">
                <option value="" <#if selectedManufacturerId?? && selectedManufacturerId == ''>selected</#if>>Все производители</option>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id}" <#if selectedManufacturerId == manufacturer.id>selected</#if>>${manufacturer.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-4 align-self-end">
            <button type="submit" class="btn btn-primary w-100">Фильтровать</button>
        </div>
    </form>

    <!-- Список товаров -->
    <div class="row">
        <#if products?size == 0>
            <p class="text-center">Товары не найдены.</p>
        <#else>
            <#list products as product>
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="${product.mainImageUrl! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                        <div class="card-body">
                            <h5 class="card-title">${product.title! 'Название товара'}</h5>
                            <p class="card-text">Цена: ${product.price! '0.00'} ₽</p>
                            <a href="/products/${product.id}" class="btn btn-primary">Подробнее</a>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</div>
</body>
</html>
