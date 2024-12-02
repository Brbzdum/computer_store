package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.repository.ManufacturerRepository;

import java.util.List;
import java.util.Optional;

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
    public List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    // Получение производителя по ID
    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id); // Возвращаем Optional<Manufacturer>
    }

    // Удаление производителя
    @Transactional
    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
