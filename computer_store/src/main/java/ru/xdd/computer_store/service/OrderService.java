package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Order;
import ru.xdd.computer_store.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Создание нового заказа
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Получение всех заказов
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // Получение заказа по ID
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Удаление заказа
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
