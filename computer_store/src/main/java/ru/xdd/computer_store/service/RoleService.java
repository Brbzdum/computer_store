package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xdd.computer_store.model.Role;
import ru.xdd.computer_store.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        Optional<Role> roleOpt = roleRepository.findByName(name);
        return roleOpt.orElseThrow(() -> new RuntimeException("Роль не найдена: " + name));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
