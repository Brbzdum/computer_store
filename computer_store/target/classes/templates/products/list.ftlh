<div class="container mt-4">
    <h1 class="text-center mb-4">Список товаров</h1>
    <div class="row">
        <#if products?size == 0>
            <p class="text-center">Товары не найдены.</p>
        <#else>
            <#list products as product>
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="${product.mainImageUrl! '/static/images/no-image.png'}" class="card-img-top" alt="Изображение товара">
                        <div class="card-body">
                            <h5 class="card-title">${product.title! 'Название товара'}</h5>
                            <p class="card-text">Цена: ${product.price! '0.00'} ₽</p>
                            <a href="/products/${product.id}" class="btn btn-primary">Подробнее</a>
                            <form action="/products/${product.id}/add-to-cart" method="post" class="mt-2">
                                <input type="hidden" name="quantity" value="1" />
                                <button type="submit" class="btn btn-success">Добавить в корзину</button>
                            </form>
                            </form>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</div>
