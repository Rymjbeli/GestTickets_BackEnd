package org.insat.helpDesk.dto;

import org.insat.helpDesk.enums.UserRole;

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

}
