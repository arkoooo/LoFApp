package kubiak.lofapp.Model;

import com.sun.istack.NotNull;
import kubiak.lofapp.Validators.PasswordMatches;
import kubiak.lofapp.Validators.ValidPassword;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@PasswordMatches(first = "password", second = "matchingPassword", message = "Hasła się nie zgadzają!")
public class UserDto {
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Pole nie może być puste!")
    @Size(min=5,message = "Nazwa użytkownika musi mieć minimum 5 znaków!")
    private String username;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Pole nie może być puste!")
    @Email
    private String mail;

    @NotNull
    @NotEmpty(message = "Pole nie może być puste!")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Pole nie może być puste!")
    private String lastName;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty(message = "Pole nie może być puste!")
    private String matchingPassword;

    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
