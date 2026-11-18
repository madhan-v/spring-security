package com.arc.to;

import com.arc.entity.Role;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
    private Role role;
}
