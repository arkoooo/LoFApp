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
        boolean isUserExists = isUserExists(userDto.getUsername(), userDto.getMail());

        if(isUserExists){
            return "userExists";
        }else {
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

    /**
     * Method check validation when user want to change his data and returns information about that.
     * Then UserController get that information and display error message. User must type his actual password to change his data.
     * @param userDto new data
     * @param user existing data
     * @return information about changing data. If user doesn't fill any field - then method returns information about that. The same
     * if new e-mail already exists in database. If everything is ok - then method return success information to Controller.
     */
    public String changeUserData(UserDto userDto, User user){
        if(userDto.getFirstName().isEmpty() || userDto.getLastName().isEmpty() || userDto.getMail().isEmpty() || userDto.getPassword().isEmpty()){
            return "fillFields";
        }if(isUserExists(userDto.getMail()) && !userDto.getMail().equals(user.getMail())){
            return "mailExists";
        }if(!checkPassword(userDto.getPassword(),user)){
            System.out.println(passwordEncoder.matches(userDto.getPassword(),user.getPassword()));
            return "invalidPassword";
        }else{
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setMail(userDto.getMail());
            userRepository.save(user);
            return "success";
        }
    }

    private boolean checkMatchingPasswords(String password, String matchingPassword){
        return password.equals(matchingPassword);
    }

    /**
     * Method checks typed password with encoded password saved in database
     * @param password raw Password
     * @param user user which we want to check password
     * @return valid or invalid typed password
     */
    private boolean checkPassword(String password, User user){
        return passwordEncoder.matches(password,user.getPassword());
    }

    /**
     * Method checks that user exists by mail and username
     * @param username user login
     * @param mail user mail
     * @return true or false
     */
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

    /**
     * Method checks that user exists by mail
     * @param mail user mail
     * @return true or false
     */
    private boolean isUserExists(String mail){
        if(userRepository.findByMail(mail) != null){
            return true;
        }else{
            return false;
        }
    }
}
