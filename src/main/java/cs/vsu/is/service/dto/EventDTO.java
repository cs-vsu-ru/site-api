package cs.vsu.is.service.dto;

import java.time.Instant;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO {
  private Long id;
  private Instant publicationDate;
  private String content;
  private Instant lastModifiedDate;
  private Instant startTime;
  private Instant endTime;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;

}
