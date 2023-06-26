package org.insat.helpDesk.service.user;

import org.insat.helpDesk.Model.User;
import org.insat.helpDesk.Repository.UserRepository;
import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;
import org.insat.helpDesk.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setFirstname(signupDTO.getFirstname());
        user.setEmail(signupDTO.getEmail());
        user.setRole(UserRole.USER);
        user.setPath(signupDTO.getPath());
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

}
