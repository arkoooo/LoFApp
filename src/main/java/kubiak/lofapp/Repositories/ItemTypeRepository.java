package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
        ItemType findById(int id);
}
