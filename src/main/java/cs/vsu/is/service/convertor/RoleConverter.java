package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Role;
import cs.vsu.is.service.dto.RoleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RoleConverter {
  private final ModelMapper modelMapper;

  public RoleConverter() {
    this.modelMapper = new ModelMapper();
  }

  public RoleDTO toDto(Role entity) {
    return modelMapper.map(entity, RoleDTO.class);
  }

  public Role toEntity(RoleDTO dto) {
    return modelMapper.map(dto, Role.class);
  }
}
