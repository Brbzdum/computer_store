package ru.xdd.computer_store.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {
    @NotEmpty(message = "Название не может быть пустым")
    private String title;

    @NotEmpty(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Цена не может быть пустой")
    private BigDecimal price;

    @NotNull(message = "Закупочная цена не может быть пустой")
    private BigDecimal purchasePrice;

    @NotNull(message = "Производитель обязателен")
    private Long manufacturerId;

    @NotNull(message = "Запас обязателен")
    private int stock;

    private MultipartFile mainImage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}

