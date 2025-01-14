<#-- manufacturers/manufacturer-list.ftlh -->
<div class="container mt-4">
    <h1 class="text-center mb-4">Производители</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list manufacturers as manufacturer>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${manufacturer.name! 'Название отсутствует'}</h5>
                        <p class="card-text">${manufacturer.description! 'Описание отсутствует'}</p>
                        <a href="/manufacturers/${manufacturer.id}" class="mt-auto btn btn-primary">
                            <i class="bi bi-eye-fill"></i> Подробнее
                        </a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
