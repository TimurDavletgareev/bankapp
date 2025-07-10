package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AllUsersDto;
import ru.yandex.practicum.dto.NewUserDto;
import ru.yandex.practicum.dto.UserFullDto;
import ru.yandex.practicum.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserFullDto getUserByEmail(@RequestParam String email) {
        return userService.getUserDtoByEmail(email);
    }

    @GetMapping("/all")
    public AllUsersDto getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/save")
    public UserFullDto addUser(@RequestBody NewUserDto newUserDto) {
        return userService.saveUser(newUserDto);
    }

    @PostMapping("/update")
    public boolean updateUser(@RequestParam String email,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String birthDate) {
        return userService.updateUser(email, name, birthDate);
    }

    @PostMapping("/update-password")
    public boolean updateUserPassword(@RequestParam String email,
                                      @RequestParam String password) {
        return userService.updatePassword(email, password);
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam String email) {
        return userService.deleteUser(email);
    }
}
