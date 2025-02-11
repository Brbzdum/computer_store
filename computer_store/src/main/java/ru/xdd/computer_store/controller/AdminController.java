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
import ru.xdd.computer_store.config.DateUtils;
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
            @Valid @ModelAttribute("productDto") ProductDto productDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("content", "admin/product-edit.ftlh");
            return "layout";
        }

        try {
            // Получение существующего продукта
            Product existingProduct = productService.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + id + " не найден"));

            // Обновление полей продукта
            existingProduct.setTitle(productDto.getTitle());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setPurchasePrice(productDto.getPurchasePrice());
            existingProduct.setStock(productDto.getStock());

            // Обновление производителя
            Manufacturer manufacturer = manufacturerService.getManufacturerById(productDto.getManufacturerId())
                    .orElseThrow(() -> new IllegalArgumentException("Производитель не найден"));
            existingProduct.setManufacturer(manufacturer);

            // Обработка загрузки изображения
            MultipartFile mainImageFile = productDto.getMainImageFile();
            productService.updateProductWithImage(existingProduct, mainImageFile);
        } catch (IOException e) {
            logger.error("Ошибка при обновлении изображения: ", e);
            model.addAttribute("errorMessage", "Ошибка при обновлении изображения");
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("content", "admin/product-edit.ftlh");
            return "layout";
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка: ", e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("content", "admin/product-edit.ftlh");
            return "layout";
        }

        return "redirect:/admin";
    }

    /**
     * Страница редактирования продукта.
     */
    @GetMapping("/product/edit/{id}")
    public String editProductPage(@PathVariable Long id, Model model,Principal principal) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + id + " не найден"));

        // Преобразование Product в ProductDto
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setPurchasePrice(product.getPurchasePrice());
        productDto.setStock(product.getStock());
        productDto.setManufacturerId(product.getManufacturer().getId());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setMainImageUrl(product.getImagePath()); // Используем imagePath

        model.addAttribute("productDto", productDto);
        addCommonAttributes(model, principal);
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("content", "admin/product-edit.ftlh");
        return "layout";
    }



    @PostMapping("/product/add")
    public String addProduct(
            @Valid @ModelAttribute("productDto") ProductDto productDto,
            BindingResult bindingResult,
            Model model) {

        logger.info("Получен запрос на добавление товара: {}", productDto);

        // Лог ошибок валидации
        if (bindingResult.hasErrors()) {
            logger.warn("Ошибки валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            model.addAttribute("content", "admin/product-add.ftlh");
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

            MultipartFile mainImageFile = productDto.getMainImageFile();
            productService.saveProductWithImage(product, mainImageFile);
            logger.info("Товар успешно сохранен!");
        } catch (IOException e) {
            logger.error("Ошибка при сохранении изображения: {}", e.getMessage());
            model.addAttribute("errorMessage", "Ошибка при сохранении изображения");
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
            model.addAttribute("content", "admin/product-add.ftlh");
            return "layout";
        }

        return "redirect:/admin";
    }

    /**
     * Страница добавления нового продукта.
     */
    @GetMapping("/product/add")
    public String addProductPage(Model model, Principal principal) {
        ProductDto productDto = new ProductDto();
        productDto.setCreatedAt(LocalDateTime.now()); // Установка текущей даты создания, если необходимо
        addCommonAttributes(model, principal);
        model.addAttribute("productDto", productDto);
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
    public String analytics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model,
            Principal principal
    ) {
        addCommonAttributes(model, principal);


        List<Object[]> popularProducts;
        List<Object[]> manufacturerPopularity;

        if (startDate != null && endDate != null) {
            popularProducts = saleService.getPopularProductsByDateRange(startDate, endDate);
            manufacturerPopularity = saleService.getManufacturerPopularityByDateRange(startDate, endDate);
        } else {
            popularProducts = saleService.getPopularProducts();
            manufacturerPopularity = saleService.getManufacturerPopularity();
        }

        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("manufacturerPopularity", manufacturerPopularity);
        model.addAttribute("content", "admin/admin-analytics.ftlh");
        return "layout";
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
            row.createCell(5).setCellValue(DateUtils.format(sale.getSaleDate()));
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

    @GetMapping("/analytics/exportExcel")
    public void exportAnalyticsToExcel(
            HttpServletResponse response,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) throws IOException {
        // Получаем данные
        List<Object[]> popularProducts = (startDate != null && endDate != null)
                ? saleService.getPopularProductsByDateRange(startDate, endDate)
                : saleService.getPopularProducts();

        List<Object[]> manufacturerPopularity = (startDate != null && endDate != null)
                ? saleService.getManufacturerPopularityByDateRange(startDate, endDate)
                : saleService.getManufacturerPopularity();

        // Создаем Excel файл
        Workbook workbook = new XSSFWorkbook();

        // Лист для популярных продуктов
        Sheet productSheet = workbook.createSheet("Популярные продукты");
        Row productHeader = productSheet.createRow(0);
        productHeader.createCell(0).setCellValue("Продукт");
        productHeader.createCell(1).setCellValue("Количество продаж");

        int productRowNum = 1;
        for (Object[] product : popularProducts) {
            Row row = productSheet.createRow(productRowNum++);
            row.createCell(0).setCellValue((String) product[0]);
            row.createCell(1).setCellValue((Long) product[1]);
        }

        // Лист для производителей
        Sheet manufacturerSheet = workbook.createSheet("Популярные производители");
        Row manufacturerHeader = manufacturerSheet.createRow(0);
        manufacturerHeader.createCell(0).setCellValue("Производитель");
        manufacturerHeader.createCell(1).setCellValue("Количество продаж");

        int manufacturerRowNum = 1;
        for (Object[] manufacturer : manufacturerPopularity) {
            Row row = manufacturerSheet.createRow(manufacturerRowNum++);
            row.createCell(0).setCellValue((String) manufacturer[0]);
            row.createCell(1).setCellValue((Long) manufacturer[1]);
        }

        // Автоподстройка ширины колонок
        for (int i = 0; i < 2; i++) {
            productSheet.autoSizeColumn(i);
            manufacturerSheet.autoSizeColumn(i);
        }

        // Настройки HTTP-ответа
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=analytics.xlsx");
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
    public String listSales(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model,
            Principal principal
    ) {
        List<Sale> sales;
        if (startDate != null && endDate != null) {
            sales = saleRepository.findSalesByDateRange(startDate, endDate);
        } else {
            sales = saleService.getAllSales();
        }
        model.addAttribute("sales", sales);
        model.addAttribute("content", "admin/sales.ftlh");
        addCommonAttributes(model, principal);
        return "layout";
    }

    /**
     * Список всех производителей (админка).
     */
    @GetMapping("/manufacturers")
    public String getAllManufacturers(Model model,Principal principal) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        addCommonAttributes(model, principal);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("content", "admin/manufacturers.ftlh"); // Указываем путь к шаблону
        return "layout"; // Возвращаем базовый шаблон
    }
    /**
     * Страница добавления нового производителя.
     */
    @GetMapping("/manufacturers/add")
    public String addManufacturerPage(Model model,Principal principal) {
        addCommonAttributes(model, principal);
        model.addAttribute("manufacturer", new Manufacturer());
        model.addAttribute("content", "admin/manufacturer-add.ftlh");
        return "layout";
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
    public String editManufacturerPage(@PathVariable Long id, Model model,Principal principal) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Производитель не найден с ID: " + id));
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("content", "admin/manufacturer-edit.ftlh");
        addCommonAttributes(model, principal);
        return "layout";
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

