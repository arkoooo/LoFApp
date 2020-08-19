package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.User;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;

    public UserController(UserRepository userRepository, ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("users/testers")
    public String showTestersPage(Model model){
        List<User> testers = userRepository.findByRoleId(3);
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("testers",testers);
        return "users/testers";
    }
}
