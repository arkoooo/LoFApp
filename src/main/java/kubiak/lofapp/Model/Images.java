package kubiak.lofapp.Model;

import javax.persistence.*;

@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Item item;

    private String urlImage1;
    private String urlImage2;
    private String urlImage3;
    private String urlImage4;
    private String urlImage5;
    private String urlImage6;

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

    public String getUrlImage1() {
        return urlImage1;
    }

    public void setUrlImage1(String urlImage1) {
        this.urlImage1 = urlImage1;
    }

    public String getUrlImage2() {
        return urlImage2;
    }

    public void setUrlImage2(String urlImage2) {
        this.urlImage2 = urlImage2;
    }

    public String getUrlImage3() {
        return urlImage3;
    }

    public void setUrlImage3(String urlImage3) {
        this.urlImage3 = urlImage3;
    }

    public String getUrlImage4() {
        return urlImage4;
    }

    public void setUrlImage4(String urlImage4) {
        this.urlImage4 = urlImage4;
    }

    public String getUrlImage5() {
        return urlImage5;
    }

    public void setUrlImage5(String urlImage5) {
        this.urlImage5 = urlImage5;
    }

    public String getUrlImage6() {
        return urlImage6;
    }

    public void setUrlImage6(String urlImage6) {
        this.urlImage6 = urlImage6;
    }
}
