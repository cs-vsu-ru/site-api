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
public class LessonDTO {

  private Long id;
  private Integer course;
  private Integer group;
  private Integer subgroup;
  private String subjectName;
  private EduSchedulePlaceDTO eduSchedulePlace;
  private ScheduleDTO schedule;
  // private EmployeeDTO employee;
}
