package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Subject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubjectDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectDTO)) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
