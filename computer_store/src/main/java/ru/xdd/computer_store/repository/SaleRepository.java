package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Sale;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Найти продажи пользователя по email
    List<Sale> findByUserEmail(String email);

    @Query("SELECT s FROM Sale s WHERE s.user.email = :email")
    List<Sale> findByUserEmailCustom(@Param("email") String email);
}
