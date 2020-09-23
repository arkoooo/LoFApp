package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Integer> {
    List<ItemCategory> findByItemTypeId(int type);
}
