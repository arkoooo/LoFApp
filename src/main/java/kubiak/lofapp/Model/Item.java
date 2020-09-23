package kubiak.lofapp.Model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private List<Image> imageList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_small_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "item")
    private List<Vote> votesList;


    private String name;

    private int fakePoints = 0;
    private int originalPoints = 0;
    private int fakeVotes = 0;
    private int originalVotes = 0;
    private String description;
    private String createDate;
    private int views;
    private String dateEnd;

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

    public int getFakePoints() {
        return fakePoints;
    }

    public void setFakePoints(int fakePoints) {
        this.fakePoints = fakePoints;
    }

    public int getOriginalPoints() {
        return originalPoints;
    }

    public void setOriginalPoints(int originalPoints) {
        this.originalPoints = originalPoints;
    }

    public int getFakeVotes() {
        return fakeVotes;
    }

    public void setFakeVotes(int fakeVotes) {
        this.fakeVotes = fakeVotes;
    }

    public int getOriginalVotes() {
        return originalVotes;
    }

    public void setOriginalVotes(int originalVotes) {
        this.originalVotes = originalVotes;
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

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
    public boolean isVoteEnd() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateEnd = formatter.parse(this.dateEnd);
        Date date = new Date();

        return dateEnd.compareTo(date) <= 0;
    }
}
