package cs.vsu.is.service.dto.fullschedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullScheduleDTO {
    private String time;
    private List<FullLessonDTO> monday;
    private List<FullLessonDTO> tuesday;
    private List<FullLessonDTO> wednesday;
    private List<FullLessonDTO> thursday;
    private List<FullLessonDTO> friday;
    private List<FullLessonDTO> saturday;
}
