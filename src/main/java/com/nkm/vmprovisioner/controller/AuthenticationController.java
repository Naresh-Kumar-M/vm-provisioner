package com.nkm.vmprovisioner.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nkm.vmprovisioner.repository.AccountRepository;
import com.nkm.vmprovisioner.security.JwtRequest;
import com.nkm.vmprovisioner.security.JwtToken;
import com.nkm.vmprovisioner.security.JwtTokenUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/Authenticate")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public JwtToken generateAuthToken(@Validated @RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(),
                authenticationRequest.getPassword()));

        return accountRepository.findByEmailId(authenticationRequest.getEmailId())
                .map(jwtTokenUtil::generateToken)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

}