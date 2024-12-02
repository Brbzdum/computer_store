package ru.xdd.computer_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Order;
import ru.xdd.computer_store.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Получение всех заказов
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    // Получение заказа по ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findOrderById(id);
        return order.map(ResponseEntity::ok)  // Если заказ найден, возвращаем 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Если не найден, 404
    }

    // Создание нового заказа
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED); // Возвращаем созданный заказ с статусом 201
    }

    // Удаление заказа по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
    }
}
