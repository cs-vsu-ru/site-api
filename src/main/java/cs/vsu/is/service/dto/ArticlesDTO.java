package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Articles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticlesDTO implements Serializable {

    private Long id;

    private Instant publicationDate;

    private String content;

    private EmployeeDTO employee;

    private AccessModesDTO accessModes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public AccessModesDTO getAccessModes() {
        return accessModes;
    }

    public void setAccessModes(AccessModesDTO accessModes) {
        this.accessModes = accessModes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlesDTO)) {
            return false;
        }

        ArticlesDTO articlesDTO = (ArticlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlesDTO{" +
            "id=" + getId() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", content='" + getContent() + "'" +
            ", employee=" + getEmployee() +
            ", accessModes=" + getAccessModes() +
            "}";
    }
}
