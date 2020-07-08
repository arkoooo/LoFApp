package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Item;
import kubiak.lofapp.Repositories.ImageRepository;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.VoteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class ItemController {
    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;
    private ImageRepository imageRepository;
    private VoteRepository voteRepository;

    public ItemController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository, ImageRepository imageRepository, VoteRepository voteRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/items/all/{id}")
    public String showAll(@PathVariable("id") int id, Model model){
        List <Item> items = itemRepository.findByItemCategoryId(id);

        model.addAttribute("items", items);
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        return "items/all";
    }
    @GetMapping("/items/details/{id}")
    public String showDetails(@PathVariable("id") int id, Model model){
        Item item = itemRepository.findById(id);

        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("detailedItems", item);
        model.addAttribute("itemImages", imageRepository.findByItemId(id));
        model.addAttribute("votes", voteRepository.findByUserIdAndItemId(1,id));

        item.setViews(item.getViews()+1);
        itemRepository.save(item);
        return "items/details";
    }
}

