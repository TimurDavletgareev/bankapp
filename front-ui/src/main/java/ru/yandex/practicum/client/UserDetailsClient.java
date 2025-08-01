package ru.yandex.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.service.ClientService;
import ru.yandex.practicum.dto.UserDetailsDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsClient implements UserDetailsService {

    private final ClientService clientService;

    @Value("${resource.alias.accounts}")
    private String resourceAlias;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername {}", username);
        String endpoint = "/user-details?email=" + username;
        UserDetailsDto userDetailsDto = clientService.get(resourceAlias, endpoint, UserDetailsDto.class);
        assert userDetailsDto != null;
        log.info("userDetailsDto {}", userDetailsDto);
        return new org.springframework.security.core.userdetails.User(
                userDetailsDto.getUsername(),
                userDetailsDto.getPassword(),
                userDetailsDto.getAuthorities());
    }
}
