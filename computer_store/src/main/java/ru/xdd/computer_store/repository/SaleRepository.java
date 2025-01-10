package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.Sale;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserEmail(String email);

}
