package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.client.AccountClient;
import ru.yandex.practicum.dto.UserFullDto;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RedirectController {

    private final AccountClient accountClient;

    @GetMapping
    public String emptyRedirect(Principal principal) {
        UserFullDto userFullDto = null;
        if (principal != null) {
            userFullDto = accountClient.getCurrentUserDto(principal.getName());
        }
        if (userFullDto == null) {
            return "redirect:/signup";
        }
        return "redirect:/main";
    }
}
