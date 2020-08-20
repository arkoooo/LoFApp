package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Vote findByUserIdAndItemId(int userId, int itemId);
    List<Vote> findByItemId(int itemId);
}
