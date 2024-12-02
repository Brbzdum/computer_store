package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Order;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Найти все заказы пользователя
    List<Order> findByUserId(Long userId);

    // Найти все заказы по дате
    List<Order> findByOrderDateBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
}
