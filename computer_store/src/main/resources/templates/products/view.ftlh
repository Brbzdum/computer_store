<!DOCTYPE html>
<html>
<head>
    <title>${product.name! 'Товар'}</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>${product.name! 'Название товара'}</h1>
    <div class="row">
        <div class="col-md-6">
            <img src="${product.mainImageUrl! '/static/images/no-image.png'}" alt="Изображение товара" class="img-fluid">
            <!-- Дополнительные изображения -->
            <div class="mt-3">
                <#list product.additionalImages as image>
                    <div class="mb-2">
                        <img src="${image.url! '/static/images/no-image.png'}" alt="Дополнительное изображение" class="img-thumbnail" width="150">
                    </div>
                </#list>
            </div>
        </div>
        <div class="col-md-6">
            <p>Описание: ${product.description! 'Описание отсутствует'}</p>
            <p>Цена: ${product.price! '0'} ₽</p>
            <p>Количество на складе: ${product.quantity! '0'}</p>
            <form action="/cart/add" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
                <input type="hidden" name="productId" value="${product.id! ''}"/>
                <button type="submit" class="btn btn-success">Добавить в корзину</button>
            </form>
            <a href="/products" class="btn btn-secondary mt-3">Вернуться к списку товаров</a>
        </div>
    </div>
</div>
</body>
</html>
