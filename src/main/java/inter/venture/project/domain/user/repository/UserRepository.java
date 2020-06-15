package inter.venture.project.domain.user.repository;

import inter.venture.project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Query("SELECT u.username from User u  WHERE u.username = ?1 and u.password = ?2")
    public String getValidUser(String username, String password);

    @Query("SELECT u.password from User u WHERE u.username = ?1")
    public String getUserPassword(String username);

}
