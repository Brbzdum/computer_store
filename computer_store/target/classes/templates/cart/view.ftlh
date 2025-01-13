<div class="container mt-4">
    <h1 class="text-center mb-4">Корзина</h1>
    <#if cart.items?size == 0>
        <p class="text-center">Ваша корзина пуста.</p>
    <#else>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Товар</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list cart.items as item>
                <tr>
                    <td>${item.product.title}</td>
                    <td>${item.product.price} ₽</td>
                    <td>${item.quantity}</td>
                    <td>
                        <form action="/cart/remove" method="post" style="display:inline;">
                            <input type="hidden" name="productId" value="${item.product.id}"/>
                            <button type="submit" class="btn btn-danger btn-sm">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="text-center mt-4">
            <form action="/cart/checkout" method="post">
                <button type="submit" class="btn btn-success btn-lg w-100">Оформить заказ</button>
            </form>
        </div>
    </#if>
</div>
