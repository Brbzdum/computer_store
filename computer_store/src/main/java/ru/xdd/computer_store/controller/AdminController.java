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
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final SaleService saleService;

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("sales", saleService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String toggleUserBan(@PathVariable Long id) {
        userService.toggleUserBan(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/edit")
    public String editUserRoles(@RequestParam("userId") Long userId, @RequestParam Map<String, String> form) {
        User user = userService.getUserById(userId);
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2,
                             @RequestParam("file3") MultipartFile file3,
                             @Valid Product product,
                             Principal principal,
                             Model model,
                             BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            return "admin-product-add";
        }

        if (product.getPrice() != null) {
            product.setPurchasePrice(product.getPrice().multiply(BigDecimal.valueOf(1.5)));
        }


        productService.saveProductWithImages(principal, product, file1, file2, file3);
        return "redirect:/admin";
    }
    @GetMapping("/admin/analytics")
    public String analytics(Model model) {
        model.addAttribute("popularProducts", saleService.getPopularProducts());
        model.addAttribute("categoryPopularity", saleService.getCategoryPopularity());
        model.addAttribute("manufacturerPopularity", saleService.getManufacturerPopularity());
        return "admin-analytics"; // Шаблон для аналитики
    }

    @GetMapping("/admin/revenue")
    public String revenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                          Model model) {
        BigDecimal revenue = saleService.calculateRevenue(startDate, endDate);
        model.addAttribute("revenue", revenue);
        return "admin-revenue"; // Шаблон для отчёта о выручке
    }


    @GetMapping("/admin/exportExcel")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException {
        List<Sale> sales = saleService.list();
        sales = sales.stream()
                .filter(sale -> sale.getSaleDate().isAfter(startDate) && sale.getSaleDate().isBefore(endDate))
                .collect(Collectors.toList());

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

}

