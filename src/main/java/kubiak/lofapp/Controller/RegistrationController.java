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
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/register")
    public String showRegistrationPage(WebRequest request, Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
        model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
        model.addAttribute("user",userDto);
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpServletRequest request, Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }else{
            userService.registerNewAccount(userDto);
            model.addAttribute("clothesCategories", itemCategoryRepository.findByType(0));
            model.addAttribute("shoesCategories", itemCategoryRepository.findByType(1));
            model.addAttribute("topViewedItems", itemRepository.findTop10ByOrderByViewsDesc());
            model.addAttribute("newestItems", itemRepository.findTop10ByOrderByCreateDateDesc());
            return "index";
        }
    }


}
