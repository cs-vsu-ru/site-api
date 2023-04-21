package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.User;
import cs.vsu.is.service.dto.UserDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserConverter {
  private final ModelMapper modelMapper;

  public UserConverter() {
    this.modelMapper = new ModelMapper();
  }

  public UserDTO toDto(User entity) {
    return modelMapper.map(entity, UserDTO.class);
  }

  public User toEntity(UserDTO dto) {
    return modelMapper.map(dto, User.class);
  }
}
