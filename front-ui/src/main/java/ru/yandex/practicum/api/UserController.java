package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.client.UserClient;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping("/user")
    public ResponseEntity<Object> getUserAccounts() {
        return ResponseEntity.ok(userClient.getAccounts());
    }
}
