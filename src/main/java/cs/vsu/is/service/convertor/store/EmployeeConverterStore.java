package cs.vsu.is.service.convertor.store;

import cs.vsu.is.service.dto.store.EmployeeDTOStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.service.dto.AdminUserDTO;

import javax.validation.Valid;

// @AllArgsConstructor
@Component
public class EmployeeConverterStore {
  private final ModelMapper modelMapper;

  public EmployeeConverterStore() {
    this.modelMapper = new ModelMapper();
    // this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public Employee toEmployeeEntity(@Valid EmployeeDTOStore dto) {
    return modelMapper.map(dto, Employee.class);
  }

  public AdminUserDTO toAdminUserDTO(@Valid EmployeeDTOStore dto) {
    return modelMapper.map(dto, AdminUserDTO.class);
  }
}
