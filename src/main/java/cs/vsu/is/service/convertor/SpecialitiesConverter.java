package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.service.dto.SpecialitiesDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SpecialitiesConverter {
  private final ModelMapper modelMapper;

  public SpecialitiesConverter() {
    this.modelMapper = new ModelMapper();
  }

  public SpecialitiesDTO toDto(Specialities entity) {
    return modelMapper.map(entity, SpecialitiesDTO.class);
  }

  public Specialities toEntity(SpecialitiesDTO dto) {
    return modelMapper.map(dto, Specialities.class);
  }
}
