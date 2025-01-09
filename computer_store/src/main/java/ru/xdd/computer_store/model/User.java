package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(max = 15, message = "Номер телефона не может содержать больше 15 символов")
    @Pattern(regexp = "^(\\+7|7|8)\\(?\\d{3}\\)?\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона должен быть в формате +7(XXX)XXX-XX-XX или 8(XXX)XXX-XX-XX")
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 30, message = "Имя не может содержать больше 30 символов")
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Column
    private String activationCode;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9]{8,}$", message = "Пароль должен содержать минимум 8 символов и включать только цифры или латинские буквы")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }
}
