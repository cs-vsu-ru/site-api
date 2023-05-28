package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentsDTO {

  private Long id;
  private String name;
	private String surname;
	private String studentPersonalNumber;

}
