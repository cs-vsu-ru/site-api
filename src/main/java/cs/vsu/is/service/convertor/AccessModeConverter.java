package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.service.dto.AccessModeDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AccessModeConverter {
  private final ModelMapper modelMapper;

  public AccessModeConverter() {
    this.modelMapper = new ModelMapper();
  }

  public AccessModeDTO toDto(AccessModes entity) {
    return modelMapper.map(entity, AccessModeDTO.class);
  }

  public AccessModes toEntity(AccessModeDTO dto) {
    return modelMapper.map(dto, AccessModes.class);
  }
}
