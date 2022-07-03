package com.nkm.vmprovisioner.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@NoArgsConstructor()
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")
    private String emailId;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private RoleEnum role;

    @NotBlank
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VirtualMachine> virtualMachines;

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }
}
