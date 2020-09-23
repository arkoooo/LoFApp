package kubiak.lofapp.Controller;

import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
    private ItemTypeRepository itemTypeRepository;

    public LoginController(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        return "login";
    }
}
