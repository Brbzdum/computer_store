package ru.xdd.computer_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.SaleService;
import ru.xdd.computer_store.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SaleService saleService;

    /**
     * Страница входа.
     */
    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", userService.getUserByPrincipal(principal));
        } else {
            model.addAttribute("user", new User());
        }
        return "login";
    }

    /**
     * Профиль текущего пользователя.
     */
    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/admin/user/roles/{id}")
    public String editUserRoles(@PathVariable Long id, @RequestParam Map<String, String> form) {
        User user = userService.getUserById(id);
        userService.changeUserRoles(user, form);
        return "redirect:/users";
    }




    /**
     * Страница регистрации.
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * Обработка регистрации.
     */
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute @Valid User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля формы");
            return "registration";
        }

        boolean created = userService.createUser(user);
        if (!created) {
            model.addAttribute("errorMessage", "Пользователь с таким email уже существует!");
            return "registration";
        }
        model.addAttribute("message", "На вашу почту отправлено письмо для подтверждения регистрации.");
        return "redirect:/users/login";
    }

    /**
     * Активация пользователя по коду.
     */
    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        boolean activated = userService.activateUser(code);
        if (activated) {
            model.addAttribute("message", "Ваш аккаунт успешно активирован!");
        } else {
            model.addAttribute("message", "Код активации недействителен!");
        }
        return "login";
    }


    /**
     * Удаление пользователя (админский функционал).
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("message", "Пользователь успешно удалён.");
        return "redirect:/users";
    }

    /**
     * Страница "Мои покупки".
     */
    @GetMapping("/profile/purchases")
    public String listUserPurchases(User buyer, Model model) {
        List<Sale> purchases = saleService.getUserPurchases(buyer);
        model.addAttribute("purchases", purchases);
        return "profile/purchases"; // Шаблон для отображения покупок
    }
}
