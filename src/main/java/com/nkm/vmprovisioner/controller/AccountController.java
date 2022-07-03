package com.nkm.vmprovisioner.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nkm.vmprovisioner.domain.Account;
import com.nkm.vmprovisioner.repository.AccountRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/Account")
@AllArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @PostMapping
    public Account createAccount(@Validated @RequestBody Account account) {
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }

    @DeleteMapping
    public void deleteLoggedInAccount() {
        getLoggedInAccount().ifPresent(accountRepository::delete);
    }
    
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    @PreAuthorize("hasRole('MASTER')")
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@Validated @NotBlank @PathVariable(name = "accountId") String accountId) {
        accountRepository.deleteById(UUID.fromString(accountId));
    }

    private Optional<Account> getLoggedInAccount() {
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByEmailId(emailId);
    }

}
