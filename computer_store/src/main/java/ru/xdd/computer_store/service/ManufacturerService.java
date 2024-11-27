package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.repository.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    // Создание нового производителя
    @Transactional
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    // Получение всех производителей
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    // Поиск производителя по ID
    public Manufacturer getManufacturerById(Long id) {
        return manufacturerRepository.findById(id).orElseThrow(() -> new RuntimeException("Производитель не найден"));
    }

    // Удаление производителя
    @Transactional
    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
