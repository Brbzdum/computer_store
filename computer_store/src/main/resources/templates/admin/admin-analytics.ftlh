

<!-- Форма фильтрации по периоду -->
<form action="/admin/analytics" method="get" class="mb-4">
    <div class="row">
        <div class="col-md-5">
            <label for="startDate" class="form-label">Начало периода</label>
            <input type="datetime-local" id="startDate" name="startDate" class="form-control" required>
        </div>
        <div class="col-md-5">
            <label for="endDate" class="form-label">Конец периода</label>
            <input type="datetime-local" id="endDate" name="endDate" class="form-control" required>
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">Применить</button>
        </div>
    </div>
</form>
<form action="/admin/analytics/exportExcel" method="get" class="mb-4">
    <div class="row">
        <div class="col-md-5">
            <label for="startDate" class="form-label">Начало периода</label>
            <input type="datetime-local" id="startDate" name="startDate" class="form-control">
        </div>
        <div class="col-md-5">
            <label for="endDate" class="form-label">Конец периода</label>
            <input type="datetime-local" id="endDate" name="endDate" class="form-control">
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-success w-100">Экспорт в Excel</button>
        </div>
    </div>
</form>

<h3>Популярные продукты</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Продукт</th>
        <th>Количество продаж</th>
    </tr>
    </thead>
    <tbody>
    <#list popularProducts as product>
        <tr>
            <td>${product[0]}</td>
            <td>${product[1]}</td>
        </tr>
    </#list>
    </tbody>
</table>

<h3>Популярные производители</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Производитель</th>
        <th>Количество продаж</th>
    </tr>
    </thead>
    <tbody>
    <#list manufacturerPopularity as manufacturer>
        <tr>
            <td>${manufacturer[0]}</td>
            <td>${manufacturer[1]}</td>
        </tr>
    </#list>
    </tbody>
</table>
