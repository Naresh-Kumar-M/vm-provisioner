package com.nkm.vmprovisioner.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nkm.vmprovisioner.repository.AccountRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmailId(email)
                .map(account -> new User(account.getEmailId(), "{noop}"+ account.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()))))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account not found with [%s]", email)));

    }
}