package kubiak.lofapp.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private ItemCategory itemCategory;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "item")
    private List<Images> imagesList;

    private String name;
    private int fake = 0;
    private int original = 0;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getFake() {
        return fake;
    }

    public void setFake(int fake) {
        this.fake = fake;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
