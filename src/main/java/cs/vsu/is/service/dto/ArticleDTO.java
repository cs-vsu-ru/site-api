package cs.vsu.is.service.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {

  private Long id;
  private String publicationDate;
  private String publicationTime;
  private String content;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;

}
