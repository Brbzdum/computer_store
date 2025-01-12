<div class="container mt-4">
    <h1 class="text-center mb-4">Список продаж</h1>
    <a href="/admin/exportExcel" class="btn btn-info mb-3">Экспортировать в Excel</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Товар</th>
            <th>Покупатель</th>
            <th>Цена продажи</th>
            <th>Дата</th>
            <th>Прибыль</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list sales as sale>
            <tr>
                <td>${sale.id}</td>
                <td>${sale.product.name}</td>
                <td>${sale.buyer.email}</td>
                <td>${sale.salePrice} ₽</td>
                <td>${sale.saleDate}</td>
                <td>${sale.salePrice - sale.purchasePrice} ₽</td>
                <td>
                    <form action="/admin/sales/delete" method="post" style="display:inline;">
                        <input type="hidden" name="saleId" value="${sale.id}">
                        <button type="submit" class="btn btn-danger btn-sm">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
