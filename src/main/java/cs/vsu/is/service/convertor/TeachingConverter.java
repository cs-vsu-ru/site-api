package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Teaching;
import cs.vsu.is.service.dto.TeachingDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TeachingConverter {
  private final ModelMapper modelMapper;

  public TeachingConverter() {
    this.modelMapper = new ModelMapper();
  }

  public TeachingDTO toDto(Teaching entity) {
    return modelMapper.map(entity, TeachingDTO.class);
  }

  public Teaching toEntity(TeachingDTO dto) {
    return modelMapper.map(dto, Teaching.class);
  }
}
