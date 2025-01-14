<div class="container mt-4">
    <h1 class="text-center mb-4">Мои покупки</h1>
    <#if purchases?size == 0>
        <p class="text-center">Вы еще ничего не купили.</p>
    <#else>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Название товара</th>
                <th>Количество</th>
                <th>Цена</th>
                <th>Дата</th>
            </tr>
            </thead>
            <tbody>
            <#list purchases as purchase>
                <tr>
                    <td>${purchase.product.title! 'Без названия'}</td>
                    <td>${purchase.quantity}</td>
                    <td>${purchase.salePrice! 0} ₽</td>
                    <td>${purchase.formattedSaleDate! 'Дата не указана'}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</div>
