package cs.vsu.is.service.convertor.update;

import cs.vsu.is.domain.Lesson;
import cs.vsu.is.service.dto.LessonDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LessonConverterUpdate {
    private final ModelMapper modelMapper;

    public LessonConverterUpdate() {
        this.modelMapper = new ModelMapper();
    }

    public void substitute(LessonDTO dto, Lesson entity) {
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Lesson lesson = modelMapper.map(dto, Lesson.class);
        modelMapper.map(lesson, entity);
    }

    public void toEntity(LessonDTO dto) {
        modelMapper.map(dto, Lesson.class);
    }
}
