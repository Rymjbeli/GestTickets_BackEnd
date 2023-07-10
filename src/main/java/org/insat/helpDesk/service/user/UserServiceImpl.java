package org.insat.helpDesk.service.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.insat.helpDesk.Model.User;
import org.insat.helpDesk.Repository.UserRepository;
import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;
import org.insat.helpDesk.enums.UserRole;
import org.insat.helpDesk.utils.EmailUtil;
import org.insat.helpDesk.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.mail.MessagingException;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpUtils otpUtils;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public UserDTO createUser(SignupDTO signupDTO, MultipartFile file) {
    String uniqueFilename;
    if(file == null) {
        uniqueFilename = "avatar.png";
    } else {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // Generate a unique filename using a timestamp or unique identifier
        uniqueFilename = generateUniqueFilename() + fileExtension;

        String uploadDir = "C:/GestTickets_FrontEnd/src/assets/Images";
        String filePath = uploadDir + "/" + uniqueFilename;
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
        String otp = otpUtils.generateOtp();

        User user = new User();
        user.setName(signupDTO.getName());
        user.setFirstname(signupDTO.getFirstname());
        user.setEmail(signupDTO.getEmail());
        user.setRole(UserRole.USER);
        user.setPath(uniqueFilename);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setName(createdUser.getName());
        userDTO.setFirstname(createdUser.getFirstname());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setRole(createdUser.getRole());
        userDTO.setPath(createdUser.getPath());
        userDTO.setOtp(createdUser.getOtp());
        userDTO.setOtpGeneratedTime(createdUser.getOtpGeneratedTime());
        try {
            emailUtil.sendOtpEmail(signupDTO.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again later");
        }
        return userDTO;
    }

    public boolean verifyAccount(String email, String otp) {
        User user = userRepository.findFirstByEmail(email);
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
        LocalDateTime.now()).getSeconds() < (1 * 60 * 60 * 24)) {
            user.setActive(true);  
            userRepository.save(user);
            return true ;
        }
    return false ;
    }
    public String regenerateOtp(String email) {
        User user = userRepository.findFirstByEmail(email);
        String otp = otpUtils.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }


    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    private String generateUniqueFilename() {
    String timestamp = String.valueOf(System.currentTimeMillis());
    String uniqueId = UUID.randomUUID().toString();
    return timestamp + "_" + uniqueId;
}

    public String forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with email " + email));
        String otp = otpUtils.generateOtp();
        try {
            emailUtil.sendSetPasswordEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send reset password email please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";

    }

    public Boolean resetPassword(String email, String password, String otp){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with email " + email));
        if (!user.getOtp().equals(otp) || Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60 * 60 * 24)) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(user);
            return true;
            }
        return false;

    }

}
