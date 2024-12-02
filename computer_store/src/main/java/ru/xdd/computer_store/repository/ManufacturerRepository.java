package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    // Найти производителя по имени
    Optional<Manufacturer> findByName(String name);
}
