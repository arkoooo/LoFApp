package kubiak.lofapp.Controller;

import kubiak.lofapp.Repositories.ItemCategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private ItemCategoryRepository itemCategoryRepository;

    public ErrorController(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @RequestMapping("/error")
    public String showErrorPage(Model model){
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
