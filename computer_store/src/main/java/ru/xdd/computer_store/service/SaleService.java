package ru.xdd.computer_store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.repository.SaleRepository;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getSalesByUserEmail(String email) {
        return saleRepository.findByUserEmail(email);
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продажа не найдена с ID: " + id));
    }

    @Transactional
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
