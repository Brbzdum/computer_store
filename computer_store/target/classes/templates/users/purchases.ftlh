<!DOCTYPE html>
<html>
<head>
    <title>Ваши покупки</title>
</head>
<body>
<h1>Ваши покупки</h1>

<#if sales?size == 0>
    <p>Вы еще ничего не купили.</p>
<#else>
    <table>
        <thead>
        <tr>
            <th>Товар</th>
            <th>Количество</th>
            <th>Цена</th>
            <th>Дата покупки</th>
        </tr>
        </thead>
        <tbody>
        <#list sales as sale>
            <tr>
                <td>${sale.product.title}</td>
                <td>${sale.quantity}</td>
                <td>${sale.salePrice} ₽</td>
                <td>${sale.saleDate?string("yyyy-MM-dd HH:mm")}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#if>

<a href="/products">Продолжить покупки</a>
</body>
</html>
