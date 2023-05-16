package cs.vsu.is.service.convertor.update;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.User;
import cs.vsu.is.service.dto.AdminUserDTO;
import cs.vsu.is.service.dto.store.EmployeeDTOStore;
import cs.vsu.is.service.dto.update.EmployeeDTOUpdate;

// @AllArgsConstructor
@Component
public class EmployeeConverterUpdate {
  private final ModelMapper modelMapper;

  public EmployeeConverterUpdate() {
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public void toEmployeeEntity(EmployeeDTOUpdate dto, Employee employee) {
    modelMapper.map(dto, employee);
  }

  public void toUserEntity(EmployeeDTOUpdate dto, User user) {
    modelMapper.map(dto, user);
  }
}
