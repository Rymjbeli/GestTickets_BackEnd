package org.insat.helpDesk.Controller;

import org.insat.helpDesk.Repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.insat.helpDesk.Exception.ResourceNotFoundException;
import org.insat.helpDesk.Model.Ticket;
import org.insat.helpDesk.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users/")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("all")
    public List<User> getAllUsers() {
        return userRepository.findAll();  
    }
    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id)
    {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User with id " + id + " not found."));       
        List<Ticket> ticket= user.getTickets();
        user.setTickets(ticket);   
        return user;        
    
    }
}