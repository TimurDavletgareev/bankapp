package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.entity.UserAccount;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.repository.AccountRepository;
import ru.yandex.practicum.repository.UserAccountRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserAccountRepository userAccountRepository;

    public List<Account> getByUserId(long userId) {
        log.info("getAccountsByUserId {}", userId);
        List<Long> accountIds = userAccountRepository.findByUserId(userId).stream()
                .map(UserAccount::getAccountId)
                .toList();
        List<Account> listToReturn = accountRepository.findByIdIn(accountIds);
        log.info("AccountsByUserId returned, list size={}", listToReturn.size());
        return listToReturn;
    }

    @Transactional
    public Account save(Long userId, String currency) {
        log.info("save account for userId={}, currency={}", userId, currency);
        boolean isValidCurrency = getByUserId(userId).stream()
                .filter(account -> currency.equalsIgnoreCase(account.getCurrency()))
                .toList()
                .isEmpty();
        if (!isValidCurrency) {
            throw new IncorrectRequestException("Currency already exists in user accounts");
        }
        Account account = new Account();
        account.setCurrency(currency);
        account.setBalance(0.0);
        account.setDeleted(false);
        account = accountRepository.save(account);
        userAccountRepository.save(UserAccount.builder()
                .userId(userId)
                .accountId(account.getId())
                .build());
        log.info("account for userId={} saved: {}", userId, account);
        return account;
    }

    @Transactional
    public Account delete(Long userId) {
        log.info("delete accounts for current userId={}", userId);
        List<Account> accounts = getByUserId(userId);
        boolean isEmptyBalances = accounts.stream()
                .filter(account -> !account.getBalance().isNaN())
                .toList()
                .isEmpty();
        if (!isEmptyBalances) {
            throw new IncorrectRequestException("Some current user accounts are not empty");
        }
        boolean isAllDeleted = accountRepository.deleteAccountsByIdIn(accounts.stream()
                .map(Account::getId)
                .toList());



                        .userAccountRepository.save(UserAccount.builder()
                .userId(userId)
                .accountId(account.getId())
                .build());
        log.info("account for userId={} saved: {}", userId, account);
        return account;
    }
}
