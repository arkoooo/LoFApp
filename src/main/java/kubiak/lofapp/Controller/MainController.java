package kubiak.lofapp.Controller;

import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;

@Controller
public class MainController {
    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;
    private ItemTypeRepository itemTypeRepository;

    public MainController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository, ItemTypeRepository itemTypeRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
        this.itemTypeRepository = itemTypeRepository;
    }

    /**
     * @param model
     * @return main page of application
     */
    @RequestMapping({"/","/main","/index",""})
    public String main(Model model) {
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        model.addAttribute("topViewedItems", itemRepository.findTop10ByOrderByViewsDesc());
        model.addAttribute("newestItems", itemRepository.findTop10ByOrderByCreateDateDesc());

        return "index";
    }
}
