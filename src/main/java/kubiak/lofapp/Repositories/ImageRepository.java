package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.Image;
import kubiak.lofapp.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByItemId(int id);
    Image findById(int id);
}
