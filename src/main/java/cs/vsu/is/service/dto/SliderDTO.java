package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Slider} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SliderDTO implements Serializable {

    private Long id;

    private String imageURL;

    private String urlTo;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrlTo() {
        return urlTo;
    }

    public void setUrlTo(String urlTo) {
        this.urlTo = urlTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SliderDTO)) {
            return false;
        }

        SliderDTO sliderDTO = (SliderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sliderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SliderDTO{" +
            "id=" + getId() +
            ", imageURL='" + getImageURL() + "'" +
            ", urlTo='" + getUrlTo() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
