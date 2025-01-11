<!DOCTYPE html>
<html>
<head>
    <title>Мои покупки</title>
</head>
<body>
<#include "/templates/fragments/layout.ftlh">

<div>
    <h1>Мои покупки</h1>
    <table class="table table-striped mt-4">
        <thead>
        <tr>
            <th>Название товара</th>
            <th>Цена</th>
            <th>Дата покупки</th>
        </tr>
        </thead>
        <tbody>
        <#list purchases as purchase>
            <tr>
                <td>${purchase.product.name! 'Название товара'}</td>
                <td>${purchase.salePrice! '0'} ₽</td>
                <td>${purchase.saleDate! 'Дата'}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>
