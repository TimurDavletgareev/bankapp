package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AccountDto;
import ru.yandex.practicum.dto.ExchangeDto;
import ru.yandex.practicum.entity.Account;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.mapper.AccountMapper;
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
            createNewAccount(userId, currency);
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
    public void updateBalance(Long fromUserId, Long toUserId, ExchangeDto exchangeDto) {
        log.info("update accounts balance: {}", exchangeDto);
        Account fromAccount = accountRepository.findByUserIdAndCurrencyName(fromUserId, exchangeDto.getFromCurrency());
        if (fromAccount == null) {
            throw new IncorrectRequestException("Account not found");
        }
        double newBalance = fromAccount.getValue() + exchangeDto.getFromValue();
        if (newBalance < 0) {
            throw new IncorrectRequestException("Not enough balance");
        }
        fromAccount.setValue(fromAccount.getValue() + exchangeDto.getFromValue());
        accountRepository.save(fromAccount);
        log.info("Successfully updated fromAccount balance for fromUserId={}, currency={}, amount={}",
                fromUserId, exchangeDto.getFromCurrency(), exchangeDto.getFromValue());
        Account toAccount = accountRepository.findByUserIdAndCurrencyName(toUserId, exchangeDto.getToCurrency());
        if (toAccount == null) {
            throw new IncorrectRequestException("Account not found");
        }
        newBalance = toAccount.getValue() + exchangeDto.getToValue();
        if (newBalance < 0) {
            throw new IncorrectRequestException("Not enough balance");
        }
        toAccount.setValue(toAccount.getValue() + exchangeDto.getToValue());
        accountRepository.save(toAccount);
        log.info("Successfully updated toAccount balance for toUserId={}, currency={}, amount={}",
                toUserId, exchangeDto.getToCurrency(), exchangeDto.getToValue());
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
