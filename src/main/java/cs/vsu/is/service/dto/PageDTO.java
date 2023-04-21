package cs.vsu.is.service.dto;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageDTO {
  private Long id;
  private String content;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;
}
