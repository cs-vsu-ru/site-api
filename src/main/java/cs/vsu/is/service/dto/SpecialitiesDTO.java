package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Specialities} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpecialitiesDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecialitiesDTO)) {
            return false;
        }

        SpecialitiesDTO specialitiesDTO = (SpecialitiesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specialitiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialitiesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
