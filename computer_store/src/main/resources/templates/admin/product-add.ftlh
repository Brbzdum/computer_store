<div class="container mt-4">
    <h1 class="text-center mb-4">Добавить товар</h1>
    <form action="/admin/product/add" method="post" enctype="multipart/form-data" class="mx-auto" style="max-width: 600px;">
        <!-- Название -->
        <div class="mb-3">
            <label for="title" class="form-label">Название</label>
            <input type="text" id="title" name="title" class="form-control" placeholder="Введите название" required>
        </div>
        <!-- Описание -->
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea id="description" name="description" class="form-control" placeholder="Введите описание" required></textarea>
        </div>
        <!-- Цена -->
        <div class="mb-3">
            <label for="price" class="form-label">Цена</label>
            <input type="number" id="price" name="price" class="form-control" placeholder="Введите цену" min="0" required>
        </div>
        <!-- Закупочная цена -->
        <div class="mb-3">
            <label for="purchasePrice" class="form-label">Закупочная цена</label>
            <input type="number" id="purchasePrice" name="purchasePrice" class="form-control" placeholder="Введите закупочную цену" min="0" required>
        </div>
        <!-- Запас -->
        <div class="mb-3">
            <label for="stock" class="form-label">Запас</label>
            <input type="number" id="stock" name="stock" class="form-control" placeholder="Введите количество товара" min="0" required>
        </div>

        <!-- Производитель -->
        <div class="mb-3">
            <label for="manufacturer" class="form-label">Производитель</label>
            <select id="manufacturer" name="manufacturerId" class="form-select" required>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id}">${manufacturer.name}</option>
                </#list>
            </select>
        </div>

        <!-- Дата создания (только отображение) -->
        <div class="mb-3">
            <label for="createdAt" class="form-label">Дата создания</label>
            <input type="text" id="createdAt" name="createdAt" class="form-control" value="${.now?string("yyyy-MM-dd HH:mm:ss")}" readonly>
        </div>

        <!-- Изображение -->
        <div class="mb-3">
            <label for="mainImage" class="form-label">Изображение</label>
            <input type="file" id="mainImage" name="mainImage" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-success w-100">Добавить</button>
    </form>
</div>
