package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RedirectController {

    @GetMapping
    public String emptyRedirect() {
        return "redirect:/main";
    }
}
