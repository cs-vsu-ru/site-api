package cs.vsu.is.service.convertor.store;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.User;
import cs.vsu.is.service.dto.AdminUserDTO;
import cs.vsu.is.service.dto.store.EmployeeDTOStore;

// @AllArgsConstructor
@Component
public class EmployeeConverterStore {
  private final ModelMapper modelMapper;

  public EmployeeConverterStore() {
    this.modelMapper = new ModelMapper();
    // this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public Employee toEmployeeEntity(EmployeeDTOStore dto) {
    return modelMapper.map(dto, Employee.class);
  }

  public AdminUserDTO toAdminUserDTO(EmployeeDTOStore dto) {
    return modelMapper.map(dto, AdminUserDTO.class);
  }
}
