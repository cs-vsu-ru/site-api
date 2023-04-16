package cs.vsu.is.service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ArticleDTO {

  private Long id;
  private Instant publicationDate;
  private String content;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;

}
