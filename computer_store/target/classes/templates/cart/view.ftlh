<!DOCTYPE html>
<html>
<head>
    <title>Корзина</title>
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<#include "../layout.ftlh">

<div class="container mt-4">
    <h1 class="text-center mb-4">Корзина</h1>
    <#if cart.items?size == 0>
        <p class="text-center">Ваша корзина пуста.</p>
    <#else>
        <table class="table">
            <thead>
            <tr>
                <th>Товар</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list cart.items as item>
                <tr>
                    <td>${item.product.title}</td>
                    <td>${item.product.price}</td>
                    <td>${item.quantity}</td>
                    <td>
                        <form action="/cart/remove/${item.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
                            <button type="submit" class="btn btn-danger">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <form action="/cart/checkout" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token! ''}"/>
            <button type="submit" class="btn btn-success w-100">Оформить заказ</button>
        </form>
    </#if>
</div>
</body>
</html>
