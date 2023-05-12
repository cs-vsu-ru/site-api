package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpecialitiesDTO implements Serializable {

  private Long id;
  private String code;
  private String name;
  @JsonIgnoreProperties(value = { "description" })
  private Set<SubjectDTO> subjects = new HashSet<>();

}
