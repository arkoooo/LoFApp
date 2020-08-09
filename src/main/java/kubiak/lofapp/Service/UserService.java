package kubiak.lofapp.Service;

import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String registerNewAccount(UserDto userDto){
        // Error handling. UserService validates the data and returns the appropriate information. When fields are
        // filled and passwords match, user doesn't exists - then UserService saves user
        if(!userDto.getPassword().isEmpty() && !userDto.getUsername().isEmpty() && !userDto.getMail().isEmpty()) {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setMail(userDto.getMail());
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            boolean isPasswordsMatch = checkMatchingPasswords(userDto.getPassword(),userDto.getMatchingPassword());
            boolean isUserExists = isUserExists(userDto.getUsername(), userDto.getMail());

            if(isPasswordsMatch){
                if(!isUserExists) {
                    if(isPasswordStrong(userDto.getPassword())){
                        System.out.println("Rejestruję");
                        userRepository.save(user);
                        return "success";
                    }else{
                        return "passwordIsTooWeak";
                    }
                }else{
                    return "userExists";
                    }
            }else{
                return "passwordDoesNotMatch";
                }
        }else{
            return "fillFields";
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
}
