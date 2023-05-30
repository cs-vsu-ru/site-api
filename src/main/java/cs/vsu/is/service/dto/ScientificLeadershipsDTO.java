package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unboundid.util.NotNull;

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
	@NotNull
	private ScientificWorkTypeDTO scientificWorkType;
	@NotNull
	private String sciWorkName;
	@NotNull
	private Set<StudentsDTO> students = new HashSet<>();
	@NotNull
	@JsonIgnoreProperties(value = { "user", "mainRole", "imageUrl", "email", "specialities", "post", "plan",
			"academicTitle", "academicDegree", "experience", "professionalExperience" })
	private EmployeeDTO employee;
}
