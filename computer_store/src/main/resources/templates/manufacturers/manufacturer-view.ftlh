<#-- manufacturers/manufacturer-view.ftlh -->
<div class="container mt-4">
    <div class="mb-4">
        <h1 class="text-center">${manufacturer.name! 'Название производителя'}</h1>
        <p class="text-center">${manufacturer.description! 'Описание производителя'}</p>
    </div>

    <h2 class="mt-5">Продукция Производителя</h2>
    <hr>
    <#if products?has_content>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <#list products as product>
                <div class="col">
                    <div class="card h-100">
                        <img src="${product.imagePath! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${product.title! 'Название товара'}</h5>
                            <p class="card-text">Цена: ${product.price! '0.00'} ₽</p>
                            <a href="/products/${product.id}" class="mt-auto btn btn-primary">
                                <i class="bi bi-info-circle-fill"></i> Подробнее
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <p class="text-center">Нет доступных продуктов для этого производителя.</p>
    </#if>
</div>
