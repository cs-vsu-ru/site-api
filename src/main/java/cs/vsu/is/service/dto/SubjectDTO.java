package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubjectDTO implements Serializable {

  private Long id;

  private String name;

  private String description;

}
