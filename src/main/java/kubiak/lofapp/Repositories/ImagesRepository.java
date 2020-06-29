package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer> {
    Images findByItemId(int id);
}
