package cs.vsu.is.service.convertor.update;

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
public class EmployeeConverterUpdate {
  private final ModelMapper modelMapper;

  public EmployeeConverterUpdate() {
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public void toEmployeeEntity(EmployeeDTOStore dto, Employee employee) {
    modelMapper.map(dto, employee);
  }

  public void toUserEntity(EmployeeDTOStore dto, User user) {
    modelMapper.map(dto, user);
  }
}
