package ru.xdd.computer_store.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.OrderDetail;



public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    // Найти все детали заказа по ID заказа
    List<OrderDetail> findByOrderId(Long orderId);

    // Найти детали по продукту (если нужно)
    List<OrderDetail> findByProductId(Long productId);

    // Поиск всех деталей по заказу и продукту (если нужно)
    Optional<OrderDetail> findByOrderIdAndProductId(Long orderId, Long productId);
}
