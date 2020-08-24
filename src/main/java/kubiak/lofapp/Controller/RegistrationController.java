package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.ItemCategoryRepository;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.UserRepository;
import kubiak.lofapp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    UserService userService;
    UserRepository userRepository;
    ItemCategoryRepository itemCategoryRepository;
    ItemRepository itemRepository;

    public RegistrationController(UserService userService, UserRepository userRepository, ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
    }

    /**
     *
     * @param request
     * @param model
     * @return Registration page
     */
    @GetMapping("/register")
    public String showRegistrationPage(WebRequest request, Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("user",userDto);
        return "register";
    }

    /**
     * Method checks if all fields are filled correctly, if not returns an error. When fields are field then new user is saved to database
     * and url is redirected to main page with message. If registerNewAccount method returns string "userExists" - that means this login
     * or mail is already used by another user.
     * @param userDto
     * @param bindingResult
     * @param request
     * @param model
     * @return main page or register page (when binding result has errors)
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpServletRequest request, Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }if(userService.registerNewAccount(userDto).equals("userExists")){
            model.addAttribute("error", "Użytkownik o podanym loginie lub adresie e-mail już istnieje w systemie!");
            return "register";
        }else{
            model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
            model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
            model.addAttribute("topViewedItems", itemRepository.findTop10ByOrderByViewsDesc());
            model.addAttribute("newestItems", itemRepository.findTop10ByOrderByCreateDateDesc());
            model.addAttribute("message","Pomyślnie zarejestrowano!");
            return "index";
        }
    }


}
