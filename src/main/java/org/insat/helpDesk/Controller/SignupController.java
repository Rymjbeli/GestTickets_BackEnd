package org.insat.helpDesk.Controller;

import java.io.File;
import java.io.IOException;

import org.insat.helpDesk.dto.SignupDTO;
import org.insat.helpDesk.dto.UserDTO;
import org.insat.helpDesk.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.core.JsonProcessingException;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SignupController {

    @Autowired
    private UserService userService;

@PostMapping("/sign-up")
  public ResponseEntity<?> signupUser(@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute SignupDTO signupDTO) {
    if (userService.hasUserWithEmail(signupDTO.getEmail())) {
      return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
    }
    
    UserDTO createdUser = userService.createUser(signupDTO, file);
    if (createdUser == null) {
      return new ResponseEntity<>("User not created. Please try again later!", HttpStatus.BAD_REQUEST);
    }
    
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }  
}
