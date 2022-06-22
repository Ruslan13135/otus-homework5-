package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ot.homework5plus.rushm.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
