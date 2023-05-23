package cs.vsu.is.service.dto.fullschedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullLessonDTO {
    private Long lessonId;
    private Boolean isDenominator;
    private String lesson;
    private Integer course;
    private String group;
    private String placement;
}
