package kubiak.lofapp.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(mappedBy = "itemType")
    private List<ItemCategory> itemCategories;

    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
