package ru.xdd.computer_store.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.dto.ProductDto;
import ru.xdd.computer_store.model.*;
import ru.xdd.computer_store.repository.SaleRepository;
import ru.xdd.computer_store.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final SaleService saleService;
    private final SaleRepository saleRepository;

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        addCommonAttributes(model, principal);
        model.addAttribute("content", "admin/admin.ftlh");
        return "layout";
    }

    private void addCommonAttributes(Model model, Principal principal ) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("users", userService.list());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("sales", saleService.list());

    }


    @PostMapping("/user/ban/{id}")
    public String toggleUserBan(@PathVariable Long id) {
        userService.toggleUserBan(id);
        return "redirect:/admin";
    }

    @PostMapping("/user/edit")
    public String editUserRoles(@RequestParam("userId") Long userId, @RequestParam Map<String, String> form) {
        User user = userService.getUserById(userId);
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }



    /**
     * Редактирование продукта.
     */
    @PostMapping("/product/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            model.addAttribute("content", "admin/product-edit.ftlh");
            return "layout";
        }
        try {
            productService.updateProductWithImage(id, product, mainImageFile);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Ошибка при обновлении изображения");
            return "admin/product-edit";
        }
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + id + " не найден"));
        model.addAttribute("product", product);
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("content", "admin/product-edit.ftlh");
        return "layout";
    }

    @PostMapping("/product/add")
    public String addProduct(
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult bindingResult,
            Model model) {

        logger.info("Получен запрос на добавление товара: {}", productDto);

        // Лог ошибок валидации
        if (bindingResult.hasErrors()) {
            logger.warn("Ошибки валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            return "layout";
        }

        try {
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setPurchasePrice(productDto.getPurchasePrice());
            product.setStock(productDto.getStock());

            Manufacturer manufacturer = manufacturerService.getManufacturerById(productDto.getManufacturerId())
                    .orElseThrow(() -> new IllegalArgumentException("Производитель не найден"));
            product.setManufacturer(manufacturer);

            if (!mainImageFile.isEmpty()) {
                product.setMainImage(mainImageFile.getBytes());
            }

            logger.info("Сохраняем товар: {}", product);
            productService.saveProductWithImage(product, mainImageFile);
            logger.info("Товар успешно сохранен!");
        } catch (IOException e) {
            logger.error("Ошибка при сохранении изображения: {}", e.getMessage());
            model.addAttribute("errorMessage", "Ошибка при сохранении изображения");
            return "layout";
        }

        return "redirect:/admin";
    }









    @GetMapping("/product/add")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("content", "admin/product-add.ftlh"); // Укажите путь к шаблону
        return "layout";
    }


    /**
     * Удаление продукта.
     */
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("popularProducts", saleService.getPopularProducts());
        model.addAttribute("manufacturerPopularity", saleService.getManufacturerPopularity());
        model.addAttribute("content", "admin/admin-analytics.ftlh"); // Возвращаем страницу добавления
        return "layout"; // Шаблон для аналитики
    }

//    @GetMapping("/revenue")
//    public String revenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
//                          Model model) {
//        BigDecimal revenue = saleService.calculateRevenue(startDate, endDate);
//        model.addAttribute("revenue", revenue);
//        return "admin-revenue"; // Шаблон для отчёта о выручке
//    }


    @GetMapping("/exportExcel")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException {
        List<Sale> sales = saleRepository.findSalesByDateRange(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales");

        // Заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Товар");
        headerRow.createCell(1).setCellValue("Производитель");
        headerRow.createCell(2).setCellValue("Покупатель");
        headerRow.createCell(3).setCellValue("Закупочная цена");
        headerRow.createCell(4).setCellValue("Цена продажи");
        headerRow.createCell(5).setCellValue("Дата продажи");
        headerRow.createCell(6).setCellValue("Прибыль");

        int rowNum = 1;
        double totalProfit = 0.0;

        for (Sale sale : sales) {
            Row row = sheet.createRow(rowNum++);
            double profit = sale.getSalePrice().doubleValue() - sale.getPurchasePrice().doubleValue();
            totalProfit += profit;

            row.createCell(0).setCellValue(sale.getProduct().getTitle());
            row.createCell(1).setCellValue(sale.getProduct().getManufacturer().getName()); // Производитель
            row.createCell(2).setCellValue(sale.getBuyer().getEmail());
            row.createCell(3).setCellValue(sale.getPurchasePrice().doubleValue());
            row.createCell(4).setCellValue(sale.getSalePrice().doubleValue());
            row.createCell(5).setCellValue(sale.getSaleDate().toString());
            row.createCell(6).setCellValue(profit);
        }

        // Итоговая строка
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(5).setCellValue("Общая прибыль:");
        totalRow.createCell(6).setCellValue(totalProfit);

        for (int i = 0; i <= 6; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=sales.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    /**
     * Удаление продажи.
     */
    @PostMapping("/sales/delete")
    public String deleteSale(@RequestParam Long saleId) {
        saleService.deleteSale(saleId);
        return "redirect:/admin/sales";
    }
    /**
     * Изменение запаса товара.
     */
    @PostMapping("/product/updateStock")
    public String updateProductStock(@RequestParam Long productId,
                                     @RequestParam int quantity,
                                     Model model) {
        try {
            productService.updateStock(productId, quantity);
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin"; // Возврат на админ-страницу с ошибкой
        }
        return "redirect:/admin";
    }
    /**
     * Отображение всех продаж.
     */
    @GetMapping("/sales")
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "admin/sales"; // Шаблон для отображения всех продаж
    }

    /**
     * Страница добавления нового производителя.
     */
    @GetMapping("/manufacturers/add")
    public String addManufacturerPage(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "admin/manufacturer-add";
    }

    /**
     * Добавление нового производителя.
     */
    @PostMapping("/manufacturers/add")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer) {
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    /**
     * Страница редактирования производителя.
     */
    @GetMapping("/manufacturers/edit/{id}")
    public String editManufacturerPage(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Производитель не найден с ID: " + id));
        model.addAttribute("manufacturer", manufacturer);
        return "admin/manufacturer-edit";
    }

    /**
     * Обновление производителя.
     */
    @PostMapping("/manufacturers/edit")
    public String editManufacturer(@ModelAttribute Manufacturer manufacturer) {
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    /**
     * Удаление производителя.
     */
    @PostMapping("/manufacturers/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return "redirect:/admin/manufacturers";
    }


}

