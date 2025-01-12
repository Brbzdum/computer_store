<div class="container mt-4">
    <h1 class="text-center mb-4">Редактировать товар</h1>
    <form action="/admin/product/edit/${product.id}" method="post" enctype="multipart/form-data" class="mx-auto" style="max-width: 600px;">
        <div class="mb-3">
            <label for="name" class="form-label">Название</label>
            <input type="text" id="name" name="name" value="${product.name}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea id="description" name="description" class="form-control" required>${product.description}</textarea>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Цена</label>
            <input type="number" id="price" name="price" value="${product.price}" class="form-control" min="0" required>
        </div>
        <div class="mb-3">
            <label for="manufacturer" class="form-label">Производитель</label>
            <select id="manufacturer" name="manufacturerId" class="form-select" required>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id}" <#if manufacturer.id == product.manufacturer.id>selected</#if>>${manufacturer.name}</option>
                </#list>
            </select>
        </div>
        <div class="mb-3">
            <label for="mainImage" class="form-label">Главное изображение</label>
            <input type="file" id="mainImage" name="mainImage" class="form-control">
            <#if product.mainImageUrl>
                <img src="${product.mainImageUrl}" alt="Главное изображение" class="img-thumbnail mt-2" width="200">
            </#if>
        </div>
        <button type="submit" class="btn btn-primary w-100">Сохранить</button>
    </form>
</div>
