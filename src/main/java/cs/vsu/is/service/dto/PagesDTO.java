package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link cs.vsu.is.domain.Pages} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PagesDTO implements Serializable {

    private Long id;

    private String content;

    private EmployeeDTO employee;

    private AccessModesDTO accessModes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof PagesDTO)) {
            return false;
        }

        PagesDTO pagesDTO = (PagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagesDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", employee=" + getEmployee() +
            ", accessModes=" + getAccessModes() +
            "}";
    }
}
