package org.insat.helpDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.insat.helpDesk.Model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
