package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Events;
import cs.vsu.is.service.dto.EventDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EventConverter {
  private final ModelMapper modelMapper;

  public EventConverter() {
    this.modelMapper = new ModelMapper();
  }

  public EventDTO toDto(Events entity) {
    return modelMapper.map(entity, EventDTO.class);
  }

  public Events toEntity(EventDTO dto) {
    return modelMapper.map(dto, Events.class);
  }
}
