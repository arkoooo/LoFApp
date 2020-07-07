package kubiak.lofapp.Model;

import javax.persistence.*;

@Entity
@Table(name="votes", uniqueConstraints={@UniqueConstraint(columnNames = {"item_id" , "user_id"})})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private User user;

    private boolean vote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vote(Item item, User user, boolean vote) {
        this.item = item;
        this.user = user;
        this.vote = vote;
    }

    public Vote() {
    }
}
