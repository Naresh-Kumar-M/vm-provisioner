package com.nkm.vmprovisioner.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "virtual_machine")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class VirtualMachine extends BaseEntity {
    private String operatingSystem;
    private long ramSize;
    private long hardDiskSize;
    private int cpuCores;
    
    @JsonIgnore
    @ManyToOne
    private Account account;
}
