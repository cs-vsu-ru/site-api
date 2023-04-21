package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubjectDTO {

  private Long id;

  private String name;

  private String description;

}
