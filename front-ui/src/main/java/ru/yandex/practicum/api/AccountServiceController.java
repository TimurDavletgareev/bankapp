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
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.error.exception.IncorrectRequestException;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountServiceController {

    private final AccountClient accountClient;
    private final PasswordEncoder passwordEncoder;

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
            throw new IncorrectRequestException("Wrong password confirmation");
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
            throw new IncorrectRequestException("Not current user login");
        }
        if (!password.equals(confirm_password)) {
            throw new IncorrectRequestException("Wrong password confirmation");
        }
        if (!accountClient.updateUserPassword(login, password)) {
            System.out.println("JKSHGSDBJSKDBLKJA passwordChangeError"); //TODO: add to errors list
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
            throw new IncorrectRequestException("Not current user login");
        }
        if (!accountClient.updateUser(login, name, birthdate)) {
            System.out.println("JKSHGSDBJSKDBLKJA updateUserError"); //TODO: add to errors list
        }
        if (!accountClient.changeAccount(login, account)) {
            System.out.println("NJVKKLJV changeAccountError"); //TODO: add to errors list
        }
        return "redirect:/main";
    }
}
