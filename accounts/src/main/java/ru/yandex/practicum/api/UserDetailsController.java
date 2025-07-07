package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-details")
@Slf4j
public class UserDetailsController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDetails> getUserById(@RequestParam String email) {
        return ResponseEntity.ok(userService.loadUserByUsername(email));
    }
}
