package cs.vsu.is.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseLessonsDTO {
    private List<LessonDTO> day1;
    private List<LessonDTO> day2;
    private List<LessonDTO> day3;
    private List<LessonDTO> day4;
    private List<LessonDTO> day5;
    private List<LessonDTO> day6;
    private List<LessonDTO> day7;
}
