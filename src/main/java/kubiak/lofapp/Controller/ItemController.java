package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Item;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class ItemController {
    ItemCategoryRepository itemCategoryRepository;
    ItemRepository itemRepository;

    public ItemController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items/all/{id}")
    public String showAll(@PathVariable("id") int id, Model model){
        model.addAttribute("items", itemRepository.findByItemCategoryId(id));
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        return "items/all";
    }
    @GetMapping("/items/details/{id}")
    public String showDetails(@PathVariable("id") int id, Model model){
        model.addAttribute("detailedItems", itemRepository.findById(id));
        return "items/details";
    }
}

