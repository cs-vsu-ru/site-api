package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Schedule;
import cs.vsu.is.service.dto.ScheduleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ScheduleConverter {
  private final ModelMapper modelMapper;

  public ScheduleConverter() {
    this.modelMapper = new ModelMapper();
  }

  public ScheduleDTO toDto(Schedule entity) {
    return modelMapper.map(entity, ScheduleDTO.class);
  }

  public Schedule toEntity(ScheduleDTO dto) {
    return modelMapper.map(dto, Schedule.class);
  }
}
