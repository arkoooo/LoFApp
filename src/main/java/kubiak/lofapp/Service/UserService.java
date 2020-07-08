package kubiak.lofapp.Service;

import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.UserDto;
import kubiak.lofapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User registerNewAccount(UserDto userDto){

        if (emailExists(userDto.getMail())) {
            System.out.println("Istnieje użytkownik o adresie " + userDto.getMail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setMail(userDto.getMail());
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByMail(email) != null;
    }
}
