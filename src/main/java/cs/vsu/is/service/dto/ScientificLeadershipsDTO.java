package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

/**
 * A DTO for the {@link cs.vsu.is.domain.ScientificLeaderships} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeadershipsDTO implements Serializable {

  private Long id;

  private Integer year;

  private StudentsDTO student;

  private ScientificWorkTypeDTO scientificWorkType;

  // private EmployeeDTO employee;

}
