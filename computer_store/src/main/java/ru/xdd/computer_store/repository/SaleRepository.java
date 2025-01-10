package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByBuyer(User buyer);

    @Query("SELECT s FROM Sale s WHERE s.saleDate > :startDate AND s.saleDate < :endDate")
    List<Sale> findSalesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
