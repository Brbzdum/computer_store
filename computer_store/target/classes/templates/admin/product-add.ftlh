<div class="container mt-4">
    <h1 class="text-center mb-4">Добавить товар</h1>
    <form action="/admin/product/add" method="post" enctype="multipart/form-data" class="mx-auto" style="max-width: 600px;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="mb-3">
            <label for="name" class="form-label">Название</label>
            <input type="text" id="name" name="name" class="form-control" placeholder="Введите название" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea id="description" name="description" class="form-control" placeholder="Введите описание" required></textarea>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Цена</label>
            <input type="number" id="price" name="price" class="form-control" placeholder="Введите цену" min="0" required>
        </div>
        <div class="mb-3">
            <label for="manufacturer" class="form-label">Производитель</label>
            <select id="manufacturer" name="manufacturerId" class="form-select" required>
                <#list manufacturers as manufacturer>
                    <option value="${manufacturer.id}">${manufacturer.name}</option>
                </#list>
            </select>
        </div>
        <div class="mb-3">
            <label for="mainImage" class="form-label">Главное изображение</label>
            <input type="file" id="mainImage" name="mainImage" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="additionalImages" class="form-label">Дополнительные изображения</label>
            <input type="file" id="additionalImages" name="additionalImages" class="form-control" multiple>
        </div>
        <button type="submit" class="btn btn-success w-100">Добавить</button>
    </form>
</div>
