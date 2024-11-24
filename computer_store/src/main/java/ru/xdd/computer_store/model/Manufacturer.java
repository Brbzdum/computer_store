package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}

