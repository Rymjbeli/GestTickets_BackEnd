package org.insat.helpDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.insat.helpDesk.Model.User;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getUserById(Long id);
    
    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

}
