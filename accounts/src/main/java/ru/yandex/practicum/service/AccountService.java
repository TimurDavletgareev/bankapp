package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.entity.UserAccount;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.mapper.AccountMapper;
import ru.yandex.practicum.model.Currency;
import ru.yandex.practicum.repository.AccountRepository;
import ru.yandex.practicum.repository.UserAccountRepository;

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
        List<Account> accountsByUserId = getAccountsByUserId(userId);
        log.info("AccountsByUserId returned, list size={}", accountsByUserId.size());
        return accountsByUserId.stream()
                .map(accountMapper::map)
                .toList();
    }

    @Transactional
    public List<AccountDto> createAccountsForNewUser(Long userId) {
        log.info("save account for userId={}", userId);
        List<AccountDto> accountDtoList = getByUserId(userId);
        for (Currency currency : Currency.values()) {
            Account account = new Account();
            account.setCurrencyTitle(currency.getTitle());
            account.setValue(0.0);
            account.setDeleted(false);
            account = accountRepository.save(account);
            userAccountRepository.save(UserAccount.builder()
                    .userId(userId)
                    .accountId(account.getId())
                    .build());
            accountDtoList.add(accountMapper.map(account));
        }
        log.info("accounts for userId={} saved", userId);
        return accountDtoList;
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
        double newBalance = account.getValue() + amount;
        if (newBalance < 0) {
            throw new IncorrectRequestException("Not enough balance");
        }
        account.setValue(account.getValue() + amount);
        accountRepository.save(account);
        log.info("Successfully updated account balance for userId={}, accountId={}, amount={}", userId, accountId, amount);
        return newBalance;
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        log.info("delete all accounts for current userId={}", userId);
        List<Account> accounts = getAccountsByUserId(userId);
        boolean isEmptyBalances = accounts.stream()
                .filter(account -> !account.getValue().isNaN())
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
