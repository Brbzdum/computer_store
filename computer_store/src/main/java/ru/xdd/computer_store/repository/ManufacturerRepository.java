package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Manufacturer;


@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {


}
