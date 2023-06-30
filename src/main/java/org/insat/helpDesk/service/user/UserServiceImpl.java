package org.insat.helpDesk.service.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.insat.helpDesk.Model.User;
import org.insat.helpDesk.Repository.UserRepository;
import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;
import org.insat.helpDesk.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


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

        User user = new User();
        user.setName(signupDTO.getName());
        user.setFirstname(signupDTO.getFirstname());
        user.setEmail(signupDTO.getEmail());
        user.setRole(UserRole.USER);
        user.setPath(uniqueFilename);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setName(createdUser.getName());
        userDTO.setFirstname(createdUser.getFirstname());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setRole(createdUser.getRole());
        userDTO.setPath(createdUser.getPath());
        return userDTO;
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


}
