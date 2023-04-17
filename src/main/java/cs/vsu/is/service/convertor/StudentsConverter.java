package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Students;
import cs.vsu.is.service.dto.StudentsDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class StudentsConverter {
  private final ModelMapper modelMapper;

  public StudentsConverter() {
    this.modelMapper = new ModelMapper();
  }

  public StudentsDTO toDto(Students entity) {
    return modelMapper.map(entity, StudentsDTO.class);
  }

  public Students toEntity(StudentsDTO dto) {
    return modelMapper.map(dto, Students.class);
  }
}
