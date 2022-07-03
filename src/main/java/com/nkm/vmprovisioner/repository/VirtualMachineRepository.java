package com.nkm.vmprovisioner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nkm.vmprovisioner.domain.Account;
import com.nkm.vmprovisioner.domain.VirtualMachine;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, UUID> {

    List<VirtualMachine> findByAccountOrderByRamSizeDesc(Account account, PageRequest pagerequest);
    
    List<VirtualMachine> findAllByOrderByRamSizeDesc(PageRequest pagerequest);
}
