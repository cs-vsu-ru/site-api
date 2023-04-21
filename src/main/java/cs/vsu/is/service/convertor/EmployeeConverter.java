package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.service.dto.EmployeeDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EmployeeConverter {
  private final ModelMapper modelMapper;

  public EmployeeConverter() {
    this.modelMapper = new ModelMapper();

  }

  public EmployeeDTO toDto(Employee entity) {
    return modelMapper.map(entity, EmployeeDTO.class);
  }

  public Employee toEntity(EmployeeDTO dto) {
    return modelMapper.map(dto, Employee.class);
  }
}
