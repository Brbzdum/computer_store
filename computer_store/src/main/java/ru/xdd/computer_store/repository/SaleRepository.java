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

    @Query("SELECT p.title, COUNT(s.id) AS salesCount " +
            "FROM Sale s JOIN s.product p " +
            "GROUP BY p.title ORDER BY salesCount DESC")
    List<Object[]> findPopularProducts();

    @Query("SELECT m.name, COUNT(s.id) AS salesCount " +
            "FROM Sale s JOIN s.product p JOIN p.manufacturer m " +
            "GROUP BY m.name ORDER BY salesCount DESC")
    List<Object[]> findManufacturerPopularity();

    @Query("SELECT p.title, COUNT(s.id) AS salesCount " +
            "FROM Sale s JOIN s.product p " +
            "WHERE s.saleDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.title ORDER BY salesCount DESC")
    List<Object[]> findPopularProductsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);


    @Query("SELECT m.name, COUNT(s.id) AS salesCount " +
            "FROM Sale s JOIN s.product p JOIN p.manufacturer m " +
            "WHERE s.saleDate BETWEEN :startDate AND :endDate " +
            "GROUP BY m.name ORDER BY salesCount DESC")
    List<Object[]> findManufacturerPopularityByDateRange(@Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate);

}
