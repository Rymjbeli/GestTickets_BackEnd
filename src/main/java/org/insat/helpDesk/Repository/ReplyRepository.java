package org.insat.helpDesk.Repository;

import java.util.List;

import org.insat.helpDesk.Model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

    List<Reply> findByTicketId(Long ticketId);
}
