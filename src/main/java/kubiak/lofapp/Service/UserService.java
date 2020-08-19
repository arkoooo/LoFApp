package kubiak.lofapp.Service;

import kubiak.lofapp.Model.Role;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.RoleRepository;
import kubiak.lofapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public String registerNewAccount(UserDto userDto) {
        Role userRole = roleRepository.findByRole("USER");
        boolean isFieldsFilled = isFieldsFilled(userDto.getUsername(), userDto.getMail(), userDto.getFirstName(), userDto.getLastName(), userDto.getPassword(), userDto.getMatchingPassword());
        boolean isPasswordsMatch = checkMatchingPasswords(userDto.getPassword(), userDto.getMatchingPassword());
        boolean isUserExists = isUserExists(userDto.getUsername(), userDto.getMail());

        // Error handling. UserService validates the data and returns the appropriate information. When fields are
        // filled and passwords match, user doesn't exists - then UserService saves user
        if(isFieldsFilled){
            return "fillFields";
        }if(!isPasswordStrong(userDto.getPassword())){
            return "passwordIsTooWeak";
        }if(isUserExists){
            return "userExists";
        }if(!isPasswordsMatch){
            return "passwordDoesNotMatch";
        }else{
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setMail(userDto.getMail());
            user.setRole(userRole);
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return "success";
        }
    }

    private boolean checkMatchingPasswords(String password, String matchingPassword){
        return password.equals(matchingPassword);
    }

    private boolean isUserExists(String username, String mail) {
        if(userRepository.findByUsername(username) != null){
            return true;
        }
        if(userRepository.findByMail(mail) != null){
            return true;
        }else{
            return false;
        }
    }
    private boolean isPasswordStrong(String password){
        return password.length() > 7;
    }
    private boolean isFieldsFilled(String username, String mail, String firstname, String lastname, String password, String matchingPassword){
        return username.isEmpty() || mail.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty() || matchingPassword.isEmpty();
    }
}
