package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@Controller
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount (@RequestBody Account account) {
        if (accountService.duplicateAccount(account.getUsername()) != null) {
            return ResponseEntity.status(409).build();
        } else if (account != null && !account.getUsername().isBlank() && account.getPassword().length() >= 4) {
            return ResponseEntity.ok(accountService.registerAccount(account));
        } else {
            return ResponseEntity.status(400).build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount (@RequestBody Account account) {
        if (accountService.loginAccount(account.getUsername(), account.getPassword()) != null) {
            return ResponseEntity.ok(accountService.loginAccount(account.getUsername(), account.getPassword()));
        }
        return ResponseEntity.status(401).build();
    }

    
}
