package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.dto.CashDto;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.mapper.AccountMapper;
import ru.yandex.practicum.model.CashOperation;
import ru.yandex.practicum.model.Currency;
import ru.yandex.practicum.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<AccountDto> getByUserId(long userId) {
        log.info("getAccountsByUserId {}", userId);
        List<Account> accountsByUserId = accountRepository.findByUserId(userId);
        log.info("AccountsByUserId returned, list size={}", accountsByUserId.size());
        return accountsByUserId.stream()
                .map(accountMapper::map)
                .toList();
    }

    @Transactional
    public boolean changeAccounts(Long userId, String accountsString) {
        log.info("changeAccounts for userId={} with accountsString={}", userId, accountsString);
        List<Account> accounts = accountRepository.findByUserId(userId);
        List<Currency> currentListOfUserCurrencies = accounts.stream()
                .filter(Account::isExists)
                .map(account -> Currency.valueOf(account.getCurrencyName()))
                .toList();
        List<Currency> newListOfUserCurrencies = new ArrayList<>();
        if (accountsString != null && !accountsString.isBlank()) {
            newListOfUserCurrencies = Arrays.stream(accountsString.split(","))
                    .map(Currency::valueOf)
                    .toList();
        }
        for (Currency currency : currentListOfUserCurrencies) {
            if (!newListOfUserCurrencies.contains(currency)) {
                changeExistedAccount(accounts, currency, false);
            }
        }
        for (Currency currency : newListOfUserCurrencies) {
            if (!currentListOfUserCurrencies.contains(currency)) {
                changeExistedAccount(accounts, currency, true);
            }
        }
        log.info("account for userId={} saved: {}", userId, accountsString);
        return true;
    }

    private void changeExistedAccount(List<Account> accountList, Currency currency, boolean isExists) {
        Account account = accountList.stream()
                .filter(ac -> ac.getCurrencyName().equals(currency.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (!isExists && account.getValue() != 0.0) {
            throw new IncorrectRequestException("Account is notnull request");
        }
        account.setExists(isExists);
        accountRepository.save(account);
    }

    public List<AccountDto> createNewUserAccounts(long userId) {
        List<AccountDto> listToReturn = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            listToReturn.add(accountMapper.map(createNewAccount(userId, currency)));
        }
        return listToReturn;
    }

    private Account createNewAccount(Long userId, Currency currency) {
        Account account = new Account();
        account.setUserId(userId);
        account.setCurrencyName(currency.name());
        account.setValue(0.0);
        account.setExists(false);
        return accountRepository.save(account);
    }

    @Transactional
    public void updateBalance(Long fromUserId, Long toUserId, TransferDto transferDto) {
        log.info("update accounts balance: {}", transferDto);
        Account fromAccount = accountRepository.findByUserIdAndCurrencyName(fromUserId, transferDto.getFromCurrency());
        if (fromAccount == null || !fromAccount.isExists()) {
            throw new IncorrectRequestException("Account not found");
        }
        double newBalance = fromAccount.getValue() - transferDto.getFromValue();
        if (newBalance < 0) {
            throw new IncorrectRequestException("Not enough balance");
        }
        fromAccount.setValue(newBalance);
        accountRepository.save(fromAccount);
        log.info("Successfully updated fromAccount balance for fromUserId={}, currency={}, new balance={}",
                fromUserId, transferDto.getFromCurrency(), newBalance);
        Account toAccount = accountRepository.findByUserIdAndCurrencyName(toUserId, transferDto.getToCurrency());
        if (toAccount == null || !toAccount.isExists()) {
            throw new IncorrectRequestException("Account not found");
        }
        newBalance = toAccount.getValue() + transferDto.getToValue();
        toAccount.setValue(newBalance);
        accountRepository.save(toAccount);
        log.info("Successfully updated toAccount balance for toUserId={}, currency={}, new balance={}",
                toUserId, transferDto.getToCurrency(), newBalance);
    }

    @Transactional
    public void cash(Long userId, CashDto cashDto) {
        log.info("cash account: {}", cashDto);
        Account account = accountRepository.findByUserIdAndCurrencyName(userId, cashDto.getCurrency());
        if (account == null || !account.isExists()) {
            throw new IncorrectRequestException("Account not found");
        }
        if (cashDto.getAction().equals(CashOperation.GET)) {
            if (account.getValue() < cashDto.getValue()) {
                throw new IncorrectRequestException("Not enough balance");
            }
            account.setValue(account.getValue() - cashDto.getValue());
        } else if (cashDto.getAction().equals(CashOperation.PUT)) {
            account.setValue(account.getValue() + cashDto.getValue());
        }
        account = accountRepository.save(account);
        log.info("Successfully cash account: {}", account);
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        log.info("delete all accounts for current userId={}", userId);
        List<Account> accounts = accountRepository.findByUserId(userId);
        boolean isEmptyBalances = accounts.stream()
                .filter(account -> !account.getValue().isNaN())
                .toList()
                .isEmpty();
        if (!isEmptyBalances) {
            throw new IncorrectRequestException("Some current user accounts are not empty");
        }
        accounts.forEach(account -> account.setExists(false));
        accountRepository.saveAll(accounts);
        log.info("All accounts for userId={} deleted", userId);
    }
}
