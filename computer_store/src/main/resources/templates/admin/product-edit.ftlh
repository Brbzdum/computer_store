<div class="container mt-4">
    <h1 class="text-center mb-4">Редактировать товар</h1>
    <#if errorMessage??>
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </#if>
    <form action="/admin/product/edit/${productDto.id}" method="post" enctype="multipart/form-data" class="mx-auto" style="max-width: 600px;">

        <!-- Название -->
        <div class="mb-3">
            <label for="title" class="form-label">Название</label>
            <input type="text" id="title" name="title" class="form-control" placeholder="Введите название" value="${productDto.title! ''}" required>
        </div>

        <!-- Описание -->
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea id="description" name="description" class="form-control" placeholder="Введите описание" required>${productDto.description! ''}</textarea>
        </div>

        <!-- Цена -->
        <div class="mb-3">
            <label for="price" class="form-label">Цена</label>
            <input type="number" id="price" name="price" class="form-control" placeholder="Введите цену" min="0" step="0.01" value="${productDto.price! '0.00'}" required>
        </div>

        <!-- Закупочная цена -->
        <div class="mb-3">
            <label for="purchasePrice" class="form-label">Закупочная цена</label>
            <input type="number" id="purchasePrice" name="purchasePrice" class="form-control" placeholder="Введите закупочную цену" min="0" step="0.01" value="${productDto.purchasePrice! '0.00'}" required>
        </div>

        <!-- Запас -->
        <div class="mb-3">
            <label for="stock" class="form-label">Запас</label>
            <input type="number" id="stock" name="stock" class="form-control" placeholder="Введите количество товара" min="0" value="${productDto.stock! 0}" required>
        </div>

        <!-- Производитель -->
        <div class="mb-3">
            <label for="manufacturer" class="form-label">Производитель</label>
            <select id="manufacturer" name="manufacturerId" class="form-select" required>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id}" <#if manufacturer.id == productDto.manufacturerId>selected</#if>>${manufacturer.name}</option>
                </#list>
            </select>
        </div>

        <!-- Дата создания (только отображение) -->
        <div class="mb-3">
            <label for="createdAt" class="form-label">Дата создания</label>
            <input type="text" id="createdAt" name="createdAt" class="form-control" value="${.now?string("yyyy-MM-dd HH:mm:ss")}" readonly>
        </div>

        <!-- Текущее изображение -->
        <div class="mb-3">
            <label class="form-label">Текущее изображение</label>
            <div class="mb-2">
                <img src="${productDto.mainImageUrl! '/static/images/no-image.png'}" alt="Изображение товара" class="img-thumbnail" style="max-width: 200px;">
            </div>
        </div>

        <!-- Загрузка нового изображения -->
        <div class="mb-3">
            <label for="mainImageFile" class="form-label">Новое изображение</label>
            <input type="file" id="mainImageFile" name="mainImageFile" class="form-control">
            <small class="form-text text-muted">Загрузите новое изображение, если хотите изменить существующее.</small>
        </div>

        <!-- Кнопка отправки формы -->
        <button type="submit" class="btn btn-primary w-100">Сохранить изменения</button>
    </form>
</div>
