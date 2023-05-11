package cs.vsu.is.service.convertor.update;

import cs.vsu.is.service.dto.EmployeeDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.User;

import javax.validation.Valid;

// @AllArgsConstructor
@Component
public class EmployeeConverterUpdate {
  private final ModelMapper modelMapper;

  public EmployeeConverterUpdate() {
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public void toEmployeeEntity(@Valid EmployeeDTO dto, Employee employee) {
    modelMapper.map(dto, employee);
  }

  public void toUserEntity(@Valid EmployeeDTO dto, User user) {
    modelMapper.map(dto, user);
  }
}
