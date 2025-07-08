package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RedirectController {

    @GetMapping
    public String authenticationRedirect(Principal principal) {
        System.out.println("AFSHSAHFASJKVHAKJCB principal " + principal);
        return "redirect:/main";

    }
}
