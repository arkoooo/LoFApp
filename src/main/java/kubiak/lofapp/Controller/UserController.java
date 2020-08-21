package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Role;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.RoleRepository;
import kubiak.lofapp.Repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private RoleRepository roleRepository;

    public UserController(UserRepository userRepository, ItemCategoryRepository itemCategoryRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("users/testers")
    public String showTestersPage(Model model){
        List<User> testers = userRepository.findByRoleId(3);
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("testers",testers);
        return "users/testers";
    }
    @GetMapping("users/menu")
    public String showUserMenu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        boolean isAdmin = false;

        if(user.getRole() == roleRepository.findByRole("ADMIN")){
            isAdmin = true;
        }

        model.addAttribute("users",user);
        model.addAttribute("isAdmin",isAdmin);
        return "users/menu";
    }
    @GetMapping("users/change-data")
    public String showChangeUserDataPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        model.addAttribute("user",user);
        return "users/change-data";
    }
}
