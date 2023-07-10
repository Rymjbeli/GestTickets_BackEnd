package org.insat.helpDesk.service.user;

import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO, MultipartFile file);

    boolean hasUserWithEmail(String email);
    
    public boolean verifyAccount(String email, String otp);

    public String regenerateOtp(String email);

    public String forgotPassword(String email);

    public Boolean resetPassword(String email, String newPassword, String otp);
}
