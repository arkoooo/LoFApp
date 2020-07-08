package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.UserRepository;
import kubiak.lofapp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationPage(WebRequest request, Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto);
        return "register";
    }
    @PostMapping("/register")
    public ModelAndView registerUser (@ModelAttribute("user") @Valid UserDto userDto, HttpServletRequest request, Errors errors){

        userService.registerNewAccount(userDto);
        return new ModelAndView("index", "user", userDto);
    }
}
