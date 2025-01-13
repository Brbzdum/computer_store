<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <!-- Карточка профиля -->
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Профиль Пользователя</h3>
                </div>
                <div class="card-body">
                    <!-- Информация о пользователе -->
                    <div class="mb-4">
                        <h5><i class="bi bi-person-fill"></i> Информация</h5>
                        <hr>
                        <div class="row">
                            <div class="col-sm-4">
                                <strong>Имя:</strong>
                            </div>
                            <div class="col-sm-8">
                                ${user.name! 'Имя не указано'}
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-4">
                                <strong>Email:</strong>
                            </div>
                            <div class="col-sm-8">
                                ${user.email! 'Email не указан'}
                            </div>
                        </div>
                    </div>
                    <!-- Действия -->
                    <div class="d-flex justify-content-between">
                        <a href="/users/profile/purchases" class="btn btn-primary">
                            <i class="bi bi-bag-check-fill"></i> Мои покупки
                        </a>
                        <form action="/logout" method="post" class="d-inline">
                            <button type="submit" class="btn btn-danger">
                                <i class="bi bi-box-arrow-right"></i> Выйти
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <!-- Конец карточки профиля -->
        </div>
    </div>
</div>
