<#-- admin/manufacturer-edit.ftlh -->
<div class="container mt-5">
    <h1 class="text-center mb-4">Редактировать Производителя</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form action="/admin/manufacturers/edit" method="post">
                <input type="hidden" name="id" value="${manufacturer.id}">
                <div class="mb-3">
                    <label for="name" class="form-label">Название Производителя</label>
                    <input type="text" class="form-control" id="name" name="name" value="${manufacturer.name!''}" required placeholder="Введите название производителя">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Описание Производителя</label>
                    <textarea class="form-control" id="description" name="description" rows="4" required placeholder="Введите описание производителя">${manufacturer.description!''}</textarea>
                </div>
                <div class="text-end">
                    <a href="/admin/manufacturers" class="btn btn-secondary me-2">
                        <i class="bi bi-arrow-left"></i> Назад
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-pencil-square"></i> Обновить
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
