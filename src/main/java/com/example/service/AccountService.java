package com.example.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount (Account account) {
        return accountRepository.save(account);
    }

    public Account duplicateAccount (String username) {
        Optional<Account> duplicateUsername = accountRepository.findByUsername(username);
        if (duplicateUsername.isPresent()) {
            return duplicateUsername.get();
        } else {
            return null;
        }
    }

    public Account loginAccount (String username, String password) {
        Optional<Account> existingAccount = accountRepository.findByUsernameAndPassword(username, password);
        if (existingAccount.isPresent()) {
            return existingAccount.get();
        } else {
            return null;
        }
    }
}
