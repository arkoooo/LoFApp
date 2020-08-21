package kubiak.lofapp.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "role")
    private List<User> user;

    private String role;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String translateRole(){
        switch (role){
            case "USER":
                return "Użytkownik";
            case "TESTER":
                return "Tester";
            case "ADMIN":
                return "Administrator";
            default:
                return "Brak";
        }
    }
}
