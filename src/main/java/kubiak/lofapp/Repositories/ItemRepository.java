package kubiak.lofapp.Repositories;

import kubiak.lofapp.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findById(int id);
    List<Item> findByItemCategoryId(int id);

}
