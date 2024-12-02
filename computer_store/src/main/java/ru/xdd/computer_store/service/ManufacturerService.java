package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.repository.ManufacturerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public Optional<Manufacturer> findManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
