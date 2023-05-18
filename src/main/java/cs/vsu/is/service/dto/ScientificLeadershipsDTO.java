package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeadershipsDTO {

  private Long id;

  private Integer year;

  private StudentsDTO student;

  private ScientificWorkTypeDTO scientificWorkType;

   private EmployeeDTO employee;

    private String studentPersonalNumber;
}
