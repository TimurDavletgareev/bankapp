package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.UserDetailsDto;
import ru.yandex.practicum.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-details")
@Slf4j
public class UserDetailsController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDetailsDto> getUserById(@RequestParam String email) {
        return ResponseEntity.ok(userService.loadUserByUsername(email));
    }
}
