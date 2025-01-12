package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.ProductService;
import ru.xdd.computer_store.service.SaleService;
import ru.xdd.computer_store.service.UserService;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final ProductService productService;
    private final UserService userService;

    public SaleController(SaleService saleService, ProductService productService, UserService userService) {
        this.saleService = saleService;
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Просмотр всех продаж (для администратора).
     */
    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "admin/sales"; // Страница с продажами
    }

    /**
     * Покупка товара.
     */
    @PostMapping("/buy")
    public String buyProduct(@RequestParam Long productId,
                             @RequestParam String buyerEmail,
                             Model model) {
        try {
            // Завершаем продажу через сервис
            saleService.completeSale(productId, buyerEmail);
            model.addAttribute("content", "success.ftlh"); // Указываем путь к шаблону успеха
            return "layout"; // Возвращаем базовый шаблон
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("content", "error.ftlh"); // Указываем путь к шаблону ошибки
            return "layout"; // Возвращаем базовый шаблон
        }
    }

}

