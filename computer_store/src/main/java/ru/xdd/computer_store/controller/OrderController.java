package ru.xdd.computer_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Order;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.OrderService;
import ru.xdd.computer_store.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    /**
     * Показать заказы текущего пользователя.
     */
    @GetMapping
    public String getUserOrders(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        return "order-list"; // Шаблон для отображения списка заказов пользователя
    }

    /**
     * Просмотр деталей заказа.
     */
    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model, Principal principal) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден с ID: " + id));
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        return "order-details"; // Шаблон для отображения деталей заказа
    }

    /**
     * Создание нового заказа (пользователем).
     */
    @PostMapping("/create")
    public String createOrder(@RequestParam("productId") Long productId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        orderService.createOrder(productId, user);
        return "redirect:/orders";
    }

    /**
     * Список всех заказов (для администратора).
     */
    @GetMapping("/admin")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-order-list"; // Шаблон для отображения всех заказов
    }

    /**
     * Обновление статуса заказа (администратором).
     */
    @PostMapping("/admin/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam("status") String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders/admin";
    }
}
