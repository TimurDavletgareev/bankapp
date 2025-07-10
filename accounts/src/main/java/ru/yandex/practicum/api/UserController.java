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

    @PostMapping("/update/{email}")
    public UserFullDto updateUser(@RequestBody UserFullDto UserFullDto,
                                  @PathVariable String email) {
        return userService.updateUser(UserFullDto, email);
    }

    @PostMapping("/update-password/{email}")
    public boolean updateUserPassword(@RequestBody String password,
                                      @PathVariable String email) {
        return userService.updatePassword(password, email);
    }

    @DeleteMapping("/delete/{email}")
    public boolean deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }
}
