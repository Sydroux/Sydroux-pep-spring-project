package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
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

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage (@RequestBody Message message) {
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && accountService.checkAccount(message.getPosted_by()) != null) {
            return ResponseEntity.ok(messageService.postMessage(message));
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessage (@PathVariable Integer message_id) {
        return ResponseEntity.ok(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage (@PathVariable Integer message_id) {
        return ResponseEntity.ok(messageService.deleteMessage(message_id));
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage (@PathVariable Integer message_id, @RequestBody Message message) {
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && messageService.getMessageById(message_id) != null) {
            return ResponseEntity.ok(messageService.updateMessage(message_id, message.getMessage_text()));
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAccountMessages (@PathVariable Integer account_id) {
        return ResponseEntity.ok(messageService.getMessageByAccountId(account_id));
    }
}
