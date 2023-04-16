package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeachingDTO {

  private Long id;
  private SubjectDTO subject;
  private SpecialitiesDTO specialities;
  // private EmployeeDTO employee;
}
