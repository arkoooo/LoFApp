package kubiak.lofapp.Controller;

import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private ItemTypeRepository itemTypeRepository;

    public ErrorController(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @RequestMapping("/error")
    public String showErrorPage(Model model){
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
