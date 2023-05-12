package cs.vsu.is.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO {
  private Long id;
  private String publicationDate;
  private String publicationTime;
  private String content;
  private String lastModifiedDate;
  private String lastModifiedTime;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String title;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;
}
