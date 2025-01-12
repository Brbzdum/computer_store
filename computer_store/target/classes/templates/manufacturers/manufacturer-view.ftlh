<div class="container mt-4">
    <h1 class="text-center mb-4">${manufacturer.name! 'Название производителя'}</h1>
    <p>${manufacturer.description! 'Описание производителя'}</p>

    <h2 class="mt-5">Продукция</h2>
    <!-- Список продуктов -->
    <div class="row">
        <#list products as product>
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${product.mainImageUrl! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                    <div class="card-body">
                        <h5 class="card-title">${product.name! 'Название товара'}</h5>
                        <p class="card-text">Цена: ${product.price! '0'} ₽</p>
                        <a href="/products/${product.id}" class="btn btn-primary">Подробнее</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
