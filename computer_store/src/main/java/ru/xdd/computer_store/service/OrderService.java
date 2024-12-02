package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Order;
import ru.xdd.computer_store.model.OrderDetail;
import ru.xdd.computer_store.repository.OrderRepository;
import ru.xdd.computer_store.repository.OrderDetailRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Создание нового заказа
    @Transactional
    public Order createOrder(Order order) {
        // Сохранение основного заказа
        Order savedOrder = orderRepository.save(order);

        // Сохранение деталей заказа
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrder(savedOrder);  // Устанавливаем связь с заказом
            orderDetailRepository.save(orderDetail);
        }
        return savedOrder;
    }

    // Получение всех заказов
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Поиск заказа по ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    // Получение всех заказов для пользователя
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Обновление заказа
    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.setTotalPrice(updatedOrder.getTotalPrice());
        // Можно добавить другие поля для обновления, если нужно
        return orderRepository.save(order);
    }

    // Удаление заказа
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
