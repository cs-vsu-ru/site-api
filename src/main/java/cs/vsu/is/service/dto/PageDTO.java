package cs.vsu.is.service.dto;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageDTO {
  private Long id;
  private String content;
  private String url;
  private String title;
  // private EmployeeDTO employee;
  private AccessModeDTO accessModes;
}
