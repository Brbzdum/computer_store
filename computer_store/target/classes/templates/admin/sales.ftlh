<div class="container mt-4">
    <h1 class="text-center mb-4">Список продаж</h1>

    <form method="get">
        <div class="row">
            <div class="col-md-5">
                <label for="startDate" class="form-label">Начало периода</label>
                <input type="datetime-local" id="startDate" name="startDate" class="form-control" required>
            </div>
            <div class="col-md-5">
                <label for="endDate" class="form-label">Конец периода</label>
                <input type="datetime-local" id="endDate" name="endDate" class="form-control" required>
            </div>
            <div class="col-md-2">
                <button type="submit" formaction="/admin/exportExcel" class="btn btn-info w-100">Экспортировать в Excel</button>
            </div>
        </div>
    </form>



    <!-- Таблица продаж -->
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
                <td>${sale.product.title}</td>
                <td>${sale.buyer.email}</td>
                <td>${sale.salePrice} ₽</td>
                <td>${sale.formattedSaleDate! 'Дата не указана'}</td>
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
