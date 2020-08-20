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
    private boolean valid;
    private boolean summedUp;

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

    public Vote(Item item, User user, boolean vote, boolean valid) {
        this.item = item;
        this.user = user;
        this.vote = vote;
        this.valid = valid;
    }

    public Vote() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isSummedUp() {
        return summedUp;
    }

    public void setSummedUp(boolean summedUp) {
        this.summedUp = summedUp;
    }
}
