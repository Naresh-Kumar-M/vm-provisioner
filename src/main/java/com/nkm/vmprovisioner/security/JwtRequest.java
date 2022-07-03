package com.nkm.vmprovisioner.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JwtRequest {
    private String emailId;
    private String password;
}
