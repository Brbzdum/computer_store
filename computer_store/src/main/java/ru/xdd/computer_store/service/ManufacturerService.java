package ru.xdd.computer_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.repository.ManufacturerRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Long id) {
        log.info("Fetching manufacturer by id: {}", id);
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        log.info("Found manufacturer: {}", manufacturer.orElse(null));
        return manufacturer;
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
