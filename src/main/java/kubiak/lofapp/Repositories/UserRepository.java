package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int id);
    User findByMail(String email);
}
