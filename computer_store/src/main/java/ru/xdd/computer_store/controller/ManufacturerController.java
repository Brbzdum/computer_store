package ru.xdd.computer_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.service.ManufacturerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    /**
     * Список всех производителей.
     */
    @GetMapping
    public String getAllManufacturers(Model model) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        model.addAttribute("manufacturers", manufacturers);
        return "manufacturer-list"; // Шаблон для отображения списка производителей
    }

    /**
     * Страница добавления нового производителя.
     */
    @GetMapping("/add")
    public String addManufacturerPage(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturer-add"; // Шаблон для добавления производителя
    }

    /**
     * Добавление нового производителя.
     */
    @PostMapping("/add")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer) {
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/manufacturers";
    }

    /**
     * Страница редактирования производителя.
     */
    @GetMapping("/edit/{id}")
    public String editManufacturerPage(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Производитель не найден с ID: " + id));
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer-edit"; // Шаблон для редактирования производителя
    }

    /**
     * Обновление производителя.
     */
    @PostMapping("/edit")
    public String editManufacturer(@ModelAttribute Manufacturer manufacturer) {
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/manufacturers";
    }

    /**
     * Удаление производителя.
     */
    @PostMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return "redirect:/manufacturers";
    }
}
