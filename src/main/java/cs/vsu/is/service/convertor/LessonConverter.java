package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Lesson;
import cs.vsu.is.service.dto.LessonDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class LessonConverter {
  private final ModelMapper modelMapper;

  public LessonConverter() {
    this.modelMapper = new ModelMapper();
  }

  public LessonDTO toDto(Lesson entity) {
    return modelMapper.map(entity, LessonDTO.class);
  }

  public Lesson toEntity(LessonDTO dto) {
    return modelMapper.map(dto, Lesson.class);
  }
}
