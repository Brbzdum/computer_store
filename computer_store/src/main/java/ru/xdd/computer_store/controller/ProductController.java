package ru.xdd.computer_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.ProductService;
import ru.xdd.computer_store.service.SaleService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SaleService saleService;

    // Главная страница с продуктами
    @GetMapping
    public String products(@RequestParam(name = "searchWord", required = false) String searchWord,
                           @RequestParam(name = "category", required = false) String category,
                           Principal principal, Model model) {
        model.addAttribute("products", productService.searchAvailableProducts(searchWord, category));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("category", category);
        return "products";
    }

    // Информация о продукте
    @GetMapping("/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    // Покупка продукта
    @PostMapping("/buy/{id}")
    public String confirmPurchase(@PathVariable Long id, Principal principal) {
        saleService.completeSale(id, principal.getName());
        return "redirect:/products";
    }

    // Создание нового продукта
    @PostMapping("/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                @Valid Product product, Principal principal, Model model) throws IOException {
        productService.saveProductWithImages(principal, product, file1, file2, file3);
        return "redirect:/products/my";
    }

    // Продукты текущего пользователя
    @GetMapping("/my")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }

    // Информация о продукте текущего пользователя
    @GetMapping("/my/{id}")
    public String myProductInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "my-product-info";
    }

    // Покупки текущего пользователя
    @GetMapping("/my/purchases")
    public String userPurchases(Principal principal, Model model) {
        List<Sale> purchases = saleService.getUserPurchases(principal);
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("purchases", purchases);
        return "my-purchases";
    }
}
