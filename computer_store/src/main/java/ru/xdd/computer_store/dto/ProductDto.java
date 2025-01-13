package ru.xdd.computer_store.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {
    private Long id; // Добавьте поле ID для редактирования

    @NotBlank(message = "Название не может быть пустым")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Цена не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть положительной")
    private BigDecimal price;

    @NotNull(message = "Закупочная цена не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Закупочная цена должна быть положительной")
    private BigDecimal purchasePrice;

    @NotNull(message = "Запас не может быть пустым")
    @Min(value = 0, message = "Запас не может быть отрицательным")
    private Integer stock;

    @NotNull(message = "Производитель должен быть выбран")
    private Long manufacturerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // Для отображения даты создания

    private MultipartFile mainImageFile;

    private String mainImageUrl;
}
