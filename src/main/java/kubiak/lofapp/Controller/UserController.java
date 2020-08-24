package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Role;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.RoleRepository;
import kubiak.lofapp.Repositories.UserRepository;
import kubiak.lofapp.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, ItemCategoryRepository itemCategoryRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    /**
     * This page lists all users with "tester" role. Users with this role can vote items and it's summed to statistics.
     * @param model
     * @return page with all testers
     */
    @GetMapping("users/testers")
    public String showTestersPage(Model model){
        Role tester = roleRepository.findByRole("TESTER");
        List<User> testers = userRepository.findByRoleId(tester.getId());
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("testers",testers);
        return "users/testers";
    }

    /**
     * It's menu when user can manage his account. If user has role admin, then have an option to go to administrator page.
     * @param model
     * @return User menu page
     */
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

    /**
     * Method shows page when user can change his data.
     * @param model
     * @return change data page
     */
    @GetMapping("users/change-data")
    public String showChangeUserDataPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        model.addAttribute("user",user);
        return "users/change-data";
    }
    @PostMapping("users/change-data")
    public String changeUserData(@ModelAttribute("user") UserDto userDto, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        switch(userService.changeUserData(userDto,user)) {
            case "success":
                model.addAttribute("message","Pomyślnie zmieniono dane!");
                model.addAttribute("users",user);
                return "users/menu";
            case "fillFields":
                model.addAttribute("error","Wypełnij wszystkie pola!");
                return "users/change-data";
            case "mailExists":
                model.addAttribute("error","Adres e-mail jest już zajęty!");
                return "users/change-data";
            case "invalidPassword":
                model.addAttribute("error","Niepoprawne hasło!");
                return "users/change-data";
            default:
                model.addAttribute("user",user);
                return "redirect:menu";
        }


    }
}
