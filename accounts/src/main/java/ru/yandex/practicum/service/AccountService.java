package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.entity.Currency;
import ru.yandex.practicum.entity.UserAccount;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.mapper.AccountMapper;
import ru.yandex.practicum.repository.AccountRepository;
import ru.yandex.practicum.repository.UserAccountRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserAccountRepository userAccountRepository;
    private final AccountMapper accountMapper;

    public List<AccountDto> getByUserId(long userId) {
        log.info("getAccountsByUserId {}", userId);
        List<Account> listToReturn = getAccountsByUserId(userId);
        log.info("AccountsByUserId returned, list size={}", listToReturn.size());
        return listToReturn.stream()
                .map(accountMapper::map)
                .toList();
    }

    @Transactional
    public AccountDto save(Long userId, String currency) {
        log.info("save account for userId={}, currency={}", userId, currency);
        boolean isValidCurrency =
                Arrays.stream(Currency.values())
                        .map(existedCurrency -> existedCurrency.toString().toUpperCase())
                        .toList()
                        .contains(currency.toUpperCase());
        if (!isValidCurrency) {
            throw new IncorrectRequestException("Invalid currency");
        }
        boolean isNewCurrency = getAccountsByUserId(userId).stream()
                .filter(account ->
                        currency.equalsIgnoreCase(account.getCurrency())
                                && !account.isDeleted()
                )
                .toList()
                .isEmpty();
        if (!isNewCurrency) {
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
        return accountMapper.map(account);
    }

    @Transactional
    public Double updateBalance(Long userId, Long accountId, Double amount) {
        log.info("update account balance for userId={}, accountId={}, amount={}", userId, accountId, amount);
        List<Long> accountIds = getAccountIdsByUserId(userId);
        if (!accountIds.contains(accountId)) {
            throw new IncorrectRequestException("Account id is not exists in user accounts");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IncorrectRequestException("Account id not found"));
        double newBalance = account.getBalance() + amount;
        if (newBalance < 0) {
            throw new IncorrectRequestException("Not enough balance");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        log.info("Successfully updated account balance for userId={}, accountId={}, amount={}", userId, accountId, amount);
        return newBalance;
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        log.info("delete all accounts for current userId={}", userId);
        List<Account> accounts = getAccountsByUserId(userId);
        boolean isEmptyBalances = accounts.stream()
                .filter(account -> !account.getBalance().isNaN())
                .toList()
                .isEmpty();
        if (!isEmptyBalances) {
            throw new IncorrectRequestException("Some current user accounts are not empty");
        }
        accounts.forEach(account -> account.setDeleted(true));
        accountRepository.saveAll(accounts);
        List<UserAccount> userAccounts = userAccountRepository.findByUserId(userId);
        userAccounts.forEach(userAccount -> userAccount.setDeleted(true));
        userAccountRepository.saveAll(userAccounts);
        log.info("All accounts for userId={} deleted", userId);
    }

    @Transactional
    public boolean deleteByAccountId(Long userId, Long accountId) {
        log.info("delete account by id={}", accountId);
        List<Long> accountIds = getAccountIdsByUserId(userId);
        if (!accountIds.contains(accountId)) {
            throw new IncorrectRequestException("Account id is not exists in user accounts");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IncorrectRequestException("Account id not found"));
        account.setDeleted(true);
        accountRepository.save(account);
        List<UserAccount> userAccounts = userAccountRepository.findByUserId(userId);
        userAccounts.forEach(userAccount -> {
            if (userAccount.getAccountId().equals(accountId)) {
                userAccount.setDeleted(true);
                userAccountRepository.save(userAccount);
            }
        });
        log.info("account with id={} deleted", accountId);
        return true;
    }

    private List<Account> getAccountsByUserId(long userId) {
        List<Long> accountIds = getAccountIdsByUserId(userId);
        return accountRepository.findByIdIn(accountIds);
    }

    private List<Long> getAccountIdsByUserId(Long userId) {
        return userAccountRepository.findByUserId(userId).stream()
                .filter(userAccount -> !userAccount.isDeleted())
                .map(UserAccount::getAccountId)
                .toList();
    }
}
