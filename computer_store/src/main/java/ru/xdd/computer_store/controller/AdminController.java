//package ru.xdd.computer_store.controller;
//
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.security.Principal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//public class AdminController {
//    private final SaleService saleService;
//    private final UserService userService;
//    private final ProductService productService;
//
//    @GetMapping("/admin")
//    public String admin(Model model, Principal principal) {
//        model.addAttribute("sales", saleService.list());
//        model.addAttribute("users", userService.list());
//        model.addAttribute("products", productService.list());
//        model.addAttribute("dateUtils", new DateUtils());
//        model.addAttribute("user", userService.getUserByPrincipal(principal));
//        return "admin";
//    }
//
//    @PostMapping("/admin/user/ban/{id}")
//    public String userBan(@PathVariable("id") Long id) {
//        userService.banUser(id);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/admin/user/edit/{user}")
//    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
//        model.addAttribute("user", user);
//        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
//        model.addAttribute("roles", Role.values());
//        return "user-edit";
//    }
//
//    @PostMapping("/admin/user/edit")
//    public String userEdit(@RequestParam("userId") Long userId, @RequestParam Map<String, String> form) {
//        User user = userService.getUserById(userId); // Найдем пользователя по ID
//        userService.changeUserRoles(user, form);    // Передаем объект User
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/admin/product/add")
//    public String addProductPage(Model model, Principal principal) {
//        model.addAttribute("product", new Product());
//        model.addAttribute("user", userService.getUserByPrincipal(principal));
//        return "admin-product-add";
//    }
//
//    @PostMapping("/admin/product/add")
//    public String addProduct(@RequestParam("file1") MultipartFile file1,
//                             @RequestParam("file2") MultipartFile file2,
//                             @RequestParam("file3") MultipartFile file3,
//                             @Valid Product product,
//                             Principal principal,
//                             Model model,
//                             BindingResult bindingResult) throws IOException {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("errorMessage", "Некорректно заполнены поля");
//            return "admin-product-add";
//        }
//
//        if (product.getPrice() != null) {
//            product.setMagprice(product.getPrice().multiply(BigDecimal.valueOf(1.5)));
//        }
//        if (product.getStatus() == null) {
//            product.setStatus("В продаже");
//        }
//        productService.saveProduct(principal, product, file1, file2, file3);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/admin/exportExcel")
//    public void exportToExcel(HttpServletResponse response,
//                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException {
//        List<Sale> sales = saleService.list()
//                .stream()
//                .filter(sale -> sale.getSaleDate().isAfter(startDate) && sale.getSaleDate().isBefore(endDate))
//                .collect(Collectors.toList());
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Sales");
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Продукт");
//        headerRow.createCell(1).setCellValue("Покупатель");
//        headerRow.createCell(2).setCellValue("Цена");
//        headerRow.createCell(3).setCellValue("Дата продажи");
//
//        int rowNum = 1;
//        for (Sale sale : sales) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(sale.getProduct().getName());
//            row.createCell(1).setCellValue(sale.getUser().getEmail());
//            row.createCell(2).setCellValue(sale.getPrice().toString());
//            row.createCell(3).setCellValue(DateUtils.format(sale.getSaleDate()));
//        }
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=sales.xlsx");
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }
//}