package cs.vsu.is.service.dto.fullschedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullScheduleDTO {
    private String time;
    private FullLessonDTO monday;
    private FullLessonDTO tuesday;
    private FullLessonDTO wednesday;
    private FullLessonDTO thursday;
    private FullLessonDTO friday;
    private FullLessonDTO saturday;
}
