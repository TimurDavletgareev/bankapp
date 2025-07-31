package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDetailsDto {
    private final String username;
    @ToString.Exclude
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
}
