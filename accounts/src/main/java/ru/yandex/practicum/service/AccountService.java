package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.repository.AccountRepository;
import ru.yandex.practicum.repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserAccountRepository userAccountRepository;

    public List<Account> getByUserId(long userId) {
        log.info("getAccountsByUserId {}", userId);
        List<Account> listToReturn = new ArrayList<>();
        userAccountRepository.findByUserId(userId)
                .forEach(userAccount -> {
                    Long accountId = userAccount.getAccountId();
                    Optional<Account> account = accountRepository.findById(accountId);
                    account.ifPresent(listToReturn::add);
                });
        log.info("AccountsByUserId returned, list size={}", listToReturn.size());
        return listToReturn;
    }
}
