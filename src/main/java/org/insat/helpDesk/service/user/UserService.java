package org.insat.helpDesk.service.user;

import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);   
}
