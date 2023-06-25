package org.insat.helpDesk.Controller;

import org.insat.helpDesk.Repository.TicketRepository;
import org.insat.helpDesk.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.insat.helpDesk.Model.User;
import org.insat.helpDesk.Exception.ResourceNotFoundException;
import org.insat.helpDesk.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("all")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();  
    }
    @GetMapping("{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        Ticket ticket= ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User with id " + id + " not found."));
        User user = ticket.getUser();
        ticket.setUser(user);  
        return ticket;                
    }

    @PostMapping("all")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        ticket.setStatus("Open");
        ticket.setDate(new java.util.Date());
        ticket.setModif(null);
        ticket.setUser(userRepository.getUserById(1L));
        return ticketRepository.save(ticket);
    }

    @PutMapping("{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketToUpdate = ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User with id " + id + " not found."));
        ticketToUpdate.setTitle(ticket.getTitle());
        ticketToUpdate.setContent(ticket.getContent());
        ticketToUpdate.setPriority(ticket.getPriority());
        ticketToUpdate.setStatus(ticket.getStatus());
        ticketToUpdate.setDate(ticket.getDate());
        return ticketRepository.save(ticketToUpdate);
    }


}