package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.User;
import cs.vsu.is.service.dto.AdminUserDTO;
import cs.vsu.is.service.dto.UserDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AdminUserConverter {
  private final ModelMapper modelMapper;

  public AdminUserConverter() {
    this.modelMapper = new ModelMapper();
  }

  public AdminUserDTO toDto(User entity) {
    return modelMapper.map(entity, AdminUserDTO.class);
  }

  public User toEntity(AdminUserDTO dto) {
    return modelMapper.map(dto, User.class);
  }
}
