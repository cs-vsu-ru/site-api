package cs.vsu.is.service.dto;

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
    private String group;
    private Integer subgroup;
    private String subjectName;
    private EduSchedulePlaceDTO eduSchedulePlace;
    private ScheduleDTO schedule;
    private String classroom;
    // private EmployeeDTO employee;
}
