package kubiak.lofapp.Controller;

import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;

    public MainController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * @param model
     * @return main page of application
     */
    @RequestMapping({"/","/main","/index",""})
    public String main(Model model) {
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("topViewedItems", itemRepository.findTop10ByOrderByViewsDesc());
        model.addAttribute("newestItems", itemRepository.findTop10ByOrderByCreateDateDesc());
        return "index";
    }
}
