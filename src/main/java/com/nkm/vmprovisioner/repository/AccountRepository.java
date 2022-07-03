package com.nkm.vmprovisioner.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkm.vmprovisioner.domain.Account;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    
    Optional<Account> findByEmailId(String emailId);

}
