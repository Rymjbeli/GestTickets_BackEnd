package org.insat.helpDesk.dto;

import java.time.LocalDateTime;

import org.insat.helpDesk.enums.UserRole;
import org.springframework.cglib.core.Local;

import lombok.Data;
@Data
public class UserDTO {
    private Long id;

    private String name;

    private String firstname;

    private String email;

    private String password;

    private UserRole role;

    private String path;

    private String otp;

    private LocalDateTime otpGeneratedTime;

}
