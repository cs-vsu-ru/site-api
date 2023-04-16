package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ScientificLeadershipsConverter {
  private final ModelMapper modelMapper;

  public ScientificLeadershipsConverter() {
    this.modelMapper = new ModelMapper();
  }

  public ScientificLeadershipsDTO toDto(ScientificLeaderships entity) {
    return modelMapper.map(entity, ScientificLeadershipsDTO.class);
  }

  public ScientificLeaderships toEntity(ScientificLeadershipsDTO dto) {
    return modelMapper.map(dto, ScientificLeaderships.class);
  }
}
