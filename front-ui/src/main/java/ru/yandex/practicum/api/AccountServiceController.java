package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.NotificationDto;
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.kafka.NotificationProducer;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountServiceController {

    private final AccountClient accountClient;
    private final PasswordEncoder passwordEncoder;
    private final NotificationProducer notificationProducer;

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam String confirm_password,
                         @RequestParam String name,
                         @RequestParam String birthdate) {
        if (!password.equals(confirm_password)) {
            notifyWrongPassword(login);
        }
        password = passwordEncoder.encode(password);
        NewUserDto newUserDto = NewUserDto.builder()
                .email(login)
                .password(password)
                .name(name)
                .birthDate(LocalDate.parse(birthdate))
                .build();
        accountClient.saveUserDto(newUserDto);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String getMainPage(Principal principal, Model model) {
        log.info("getMainPage for {}", principal.getName());
        UserFullDto userFullDto = accountClient.getCurrentUserDto(principal.getName());
        model.addAttribute("login", userFullDto.getEmail());
        model.addAttribute("name", userFullDto.getName());
        model.addAttribute("birthdate", userFullDto.getBirthDate());
        model.addAttribute("accounts", userFullDto.getAccounts());
        model.addAttribute("users", accountClient.getAllUsers().getUsers());
        model.addAttribute("currency", accountClient.getAllCurrencies());
        return "main";
    }

    @PostMapping("/user/{login}/editPassword")
    public String editUserPassword(@PathVariable String login,
                                   @RequestParam String password,
                                   @RequestParam String confirm_password,
                                   Principal principal) {
        if (!login.equals(principal.getName())) {
            notifyWrongLogin(principal.getName());
        }
        if (!password.equals(confirm_password)) {
            notifyWrongPassword(principal.getName());
        }
        if (!accountClient.updateUserPassword(login, password)) {
            notifyError(principal.getName(), "Password change failed");
        }
        return "redirect:/main";
    }

    @PostMapping("/user/{login}/editUserAccounts")
    public String updateUser(@PathVariable String login,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String birthdate,
                             @RequestParam(required = false) String account,
                             Principal principal) {
        if (!login.equals(principal.getName())) {
            notifyWrongLogin(principal.getName());
        }
        if (!accountClient.updateUser(login, name, birthdate)) {
            notifyError(principal.getName(), "User update failed");
        }
        if (!accountClient.changeAccount(login, account)) {
            notifyError(principal.getName(), "Account change failed");
        }
        return "redirect:/main";
    }

    private void notifyWrongLogin(String email) {
        String subject = "Not current user login";
        String message = "Login and principal email do not match";
        notificationProducer.notify(new NotificationDto(email,  subject, message));
        throw new IncorrectRequestException(subject);
    }

    private void notifyWrongPassword(String email) {
        String subject = "Wrong password confirmation";
        String message = "Passwords do not match";
        notificationProducer.notify(new NotificationDto(email,  subject, message));
        throw new IncorrectRequestException(subject);
    }

    private void notifyError(String email, String subject) {
        String message = "Something went wrong";
        notificationProducer.notify(new NotificationDto(email,  subject, message));
        throw new IncorrectRequestException(subject);
    }
}
