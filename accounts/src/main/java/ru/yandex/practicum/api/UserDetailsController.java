package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-details")
@Slf4j
public class UserDetailsController {

    private final UserService userService;

    @GetMapping("/{email}")
    public UserDetails getUserById(@PathVariable String email) {
        return userService.loadUserByUsername(email);
    }
}
