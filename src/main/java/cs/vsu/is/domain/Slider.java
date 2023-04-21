package cs.vsu.is.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Slider.
 */
@Entity
@Table(name = "slider")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Slider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "url_to")
    private String urlTo;

    @Column(name = "title")
    private String title;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Slider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Slider imageURL(String imageURL) {
        this.setImageURL(imageURL);
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrlTo() {
        return this.urlTo;
    }

    public Slider urlTo(String urlTo) {
        this.setUrlTo(urlTo);
        return this;
    }

    public void setUrlTo(String urlTo) {
        this.urlTo = urlTo;
    }

    public String getTitle() {
        return this.title;
    }

    public Slider title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slider)) {
            return false;
        }
        return id != null && id.equals(((Slider) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Slider{" +
            "id=" + getId() +
            ", imageURL='" + getImageURL() + "'" +
            ", urlTo='" + getUrlTo() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
