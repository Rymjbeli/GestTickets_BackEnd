package org.insat.helpDesk.Repository;

import org.insat.helpDesk.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    Ticket findTicketById(Long id);

    List<Ticket> findTicketsByStatus(String status);

    List<Ticket> findByUserId(Long id);
    
}
