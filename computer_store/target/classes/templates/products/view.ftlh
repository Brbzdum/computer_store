<div class="container mt-4">
    <h1 class="text-center mb-4">${product.title! 'Название товара'}</h1>
    <div class="row">
        <div class="col-md-6">
            <!-- Отображаем только основное изображение -->
            <img src="${product.imagePath! '/static/images/no-image.png'}"
                 alt="Изображение товара" class="img-fluid">
        </div>
        <div class="col-md-6">
            <p><strong>Описание:</strong> ${product.description! 'Описание отсутствует'}</p>
            <p><strong>Цена:</strong> ${product.price! '0.00'} ₽</p>
            <p><strong>Количество на складе:</strong> ${product.stock! '0'}</p>
            <form action="/products/${product.id}/add-to-cart" method="post" class="mt-2">
                <input type="hidden" name="quantity" value="1" />
                <button type="submit" class="btn btn-success">Добавить в корзину</button>
            </form>
            <a href="/products" class="btn btn-secondary mt-3">Вернуться к списку товаров</a>
        </div>
    </div>
</div>
