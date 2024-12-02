package ru.xdd.computer_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.service.ManufacturerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    // Получение всех производителей
    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.findAllManufacturers();  // Возвращаем все производителей
    }

    // Получение производителя по ID
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturerById(id);
        return manufacturer
                .map(ResponseEntity::ok)  // Если производитель найден, возвращаем 200 OK с данным производителем
                .orElseGet(() -> ResponseEntity.notFound().build());  // Если не найден, возвращаем 404
    }

    // Создание нового производителя
    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer newManufacturer = manufacturerService.createManufacturer(manufacturer);
        return new ResponseEntity<>(newManufacturer, HttpStatus.CREATED);  // Возвращаем созданного производителя с статусом 201
    }

    // Удаление производителя по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();  // Возвращаем статус 204 No Content, если удаление прошло успешно
    }
}
