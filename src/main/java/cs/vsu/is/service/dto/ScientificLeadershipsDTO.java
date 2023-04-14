package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.ScientificLeaderships} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeadershipsDTO implements Serializable {

    private Long id;

    private Integer year;

    private StudentsDTO student;

    private ScientificWorkTypeDTO scientificWorkType;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public StudentsDTO getStudent() {
        return student;
    }

    public void setStudent(StudentsDTO student) {
        this.student = student;
    }

    public ScientificWorkTypeDTO getScientificWorkType() {
        return scientificWorkType;
    }

    public void setScientificWorkType(ScientificWorkTypeDTO scientificWorkType) {
        this.scientificWorkType = scientificWorkType;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScientificLeadershipsDTO)) {
            return false;
        }

        ScientificLeadershipsDTO scientificLeadershipsDTO = (ScientificLeadershipsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scientificLeadershipsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScientificLeadershipsDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", student=" + getStudent() +
            ", scientificWorkType=" + getScientificWorkType() +
            ", employee=" + getEmployee() +
            "}";
    }
}
