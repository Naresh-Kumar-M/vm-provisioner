package com.nkm.vmprovisioner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkm.vmprovisioner.domain.Account;
import com.nkm.vmprovisioner.domain.VirtualMachine;
import com.nkm.vmprovisioner.repository.AccountRepository;
import com.nkm.vmprovisioner.repository.VirtualMachineRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/VirtualMachine")
@AllArgsConstructor
public class VirtualMachineController {

    private final VirtualMachineRepository virtualMachineRepository;
    private final AccountRepository accountRepository;

    @PostMapping
    public VirtualMachine createVirtualMachine(@Validated @RequestBody VirtualMachine virtualMachine) {
        getLoggedInAccount().ifPresent(virtualMachine::setAccount);
        return virtualMachineRepository.save(virtualMachine);
    }

    @GetMapping
    public List<VirtualMachine> getVirtualMachines() {
        return getLoggedInAccount()
                .map(Account::getVirtualMachines)
                .orElseGet(ArrayList::new);
    }
    
    @GetMapping("/TopN")
    public List<VirtualMachine> getTopNVirtualMachinesByRamSize(
            @RequestParam(name = "Count", required = true) int count) {
        return getLoggedInAccount()
                .map(account -> virtualMachineRepository.findByAccountOrderByRamSizeDesc(account, PageRequest.of(0, count)))
                .orElseGet(ArrayList::new);
    }
    
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/TopNAll")
    public List<VirtualMachine> getTopNAllVirtualMachinesByRamSize(
            @RequestParam(name = "Count", required = true) int count) {
            return virtualMachineRepository.findAllByOrderByRamSizeDesc(PageRequest.of(0, count));
    }
    
    
    private Optional<Account> getLoggedInAccount() {
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByEmailId(emailId);
    }

}
