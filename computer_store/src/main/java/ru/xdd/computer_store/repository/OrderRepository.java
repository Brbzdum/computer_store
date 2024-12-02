package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Order;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Поиск заказов по пользователю
    List<Order> findByUserId(Long userId);

    // Поиск заказов по дате
    List<Order> findByOrderDateBetween(String startDate, String endDate);
}
