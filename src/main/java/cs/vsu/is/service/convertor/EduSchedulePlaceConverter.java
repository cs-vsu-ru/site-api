package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.service.dto.EduSchedulePlaceDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EduSchedulePlaceConverter {
  private final ModelMapper modelMapper;

  public EduSchedulePlaceConverter() {
    this.modelMapper = new ModelMapper();
  }

  public EduSchedulePlaceDTO toDto(EduSchedulePlace entity) {
    return modelMapper.map(entity, EduSchedulePlaceDTO.class);
  }

  public EduSchedulePlace toEntity(EduSchedulePlaceDTO dto) {
    return modelMapper.map(dto, EduSchedulePlace.class);
  }
}
