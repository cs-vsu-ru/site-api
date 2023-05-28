package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeadershipsDTO {
  private Long id;
  private Integer year;
	private ScientificWorkTypeDTO scientificWorkType;
	private String sciWorkName;
	
  private Set<StudentsDTO> students = new HashSet<>();
  @JsonIgnoreProperties(value = { "specialities" })
  private EmployeeDTO employee;
}
