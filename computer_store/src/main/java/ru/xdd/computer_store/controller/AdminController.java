package ru.xdd.computer_store.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.*;
import ru.xdd.computer_store.repository.SaleRepository;
import ru.xdd.computer_store.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final SaleService saleService;
    private final SaleRepository saleRepository;

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("sales", saleService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
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
     * Добавление нового продукта.
     */
    @PostMapping("/product/add")
    public String addProduct(@RequestParam("mainImage") MultipartFile mainImageFile,
                             @RequestParam("additionalImages") MultipartFile[] additionalImageFiles,
                             @Valid @ModelAttribute Product product,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            return "admin/product-add";
        }
        try {
            productService.saveProductWithImages(product, mainImageFile, additionalImageFiles);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении изображения");
            return "admin/product-add";
        }
        return "redirect:/admin";
    }
    /**
     * Редактирование продукта.
     */
    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @RequestParam("mainImage") MultipartFile mainImageFile,
                              @RequestParam("additionalImages") MultipartFile[] additionalImageFiles,
                              @Valid @ModelAttribute Product product,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            return "admin/product-edit";
        }
        try {
            productService.updateProductWithImages(id, product, mainImageFile, additionalImageFiles);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Ошибка при обновлении изображения");
            return "admin/product-edit";
        }
        return "redirect:/admin";
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
        model.addAttribute("categoryPopularity", saleService.getCategoryPopularity());
        model.addAttribute("manufacturerPopularity", saleService.getManufacturerPopularity());
        return "admin-analytics"; // Шаблон для аналитики
    }

    @GetMapping("/revenue")
    public String revenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                          Model model) {
        BigDecimal revenue = saleService.calculateRevenue(startDate, endDate);
        model.addAttribute("revenue", revenue);
        return "admin-revenue"; // Шаблон для отчёта о выручке
    }


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
    /**
     * Список всех категорий.
     */
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category-list"; // Шаблон для отображения списка категорий
    }

    /**
     * Страница добавления новой категории.
     */
    @GetMapping("/categories/add")
    public String addCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-add"; // Шаблон для добавления категории
    }

    /**
     * Добавление новой категории.
     */
    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    /**
     * Страница редактирования категории.
     */
    @GetMapping("/categories/edit/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена с ID: " + id));
        model.addAttribute("category", category);
        return "admin/category-edit"; // Шаблон для редактирования категории
    }

    /**
     * Обновление категории.
     */
    @PostMapping("/categories/edit")
    public String editCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    /**
     * Удаление категории.
     */
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

}

