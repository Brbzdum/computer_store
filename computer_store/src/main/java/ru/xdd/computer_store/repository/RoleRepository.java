package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
