package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Subject;
import cs.vsu.is.service.dto.SubjectDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SubjectConverter {
  private final ModelMapper modelMapper;

  public SubjectConverter() {
    this.modelMapper = new ModelMapper();
  }

  public SubjectDTO toDto(Subject entity) {
    return modelMapper.map(entity, SubjectDTO.class);
  }

  public Subject toEntity(SubjectDTO dto) {
    return modelMapper.map(dto, Subject.class);
  }
}
