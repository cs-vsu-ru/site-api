package cs.vsu.is.service.convertor;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.service.dto.AdminEmployeeDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import lombok.AllArgsConstructor;

// @AllArgsConstructor
@Component
public class EmployeeConverter {
  private final ModelMapper modelMapper;

  public EmployeeConverter() {
    this.modelMapper = new ModelMapper();
  }

  public EmployeeDTO toDto(Employee entity) {
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    EmployeeDTO dto = modelMapper.map(entity, EmployeeDTO.class);
    modelMapper.map(entity.getUser(), dto);
    return dto;
  }

  public AdminEmployeeDTO toAdminDto(Employee entity) {
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    AdminEmployeeDTO dto = modelMapper.map(entity, AdminEmployeeDTO.class);
    modelMapper.map(entity.getUser(), dto);
    return dto;
  }

  public Employee toEntity(EmployeeDTO dto) {
    return modelMapper.map(dto, Employee.class);
  }
}
