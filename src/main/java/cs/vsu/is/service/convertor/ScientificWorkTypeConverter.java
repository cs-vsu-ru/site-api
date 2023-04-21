package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ScientificWorkTypeConverter {
  private final ModelMapper modelMapper;

  public ScientificWorkTypeConverter() {
    this.modelMapper = new ModelMapper();
  }

  public ScientificWorkTypeDTO toDto(ScientificWorkType entity) {
    return modelMapper.map(entity, ScientificWorkTypeDTO.class);
  }

  public ScientificWorkType toEntity(ScientificWorkTypeDTO dto) {
    return modelMapper.map(dto, ScientificWorkType.class);
  }
}
