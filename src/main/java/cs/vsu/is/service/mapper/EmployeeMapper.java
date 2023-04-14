package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Role;
import cs.vsu.is.domain.User;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.RoleDTO;
import cs.vsu.is.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNameSet")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "removeRole", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("roleName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO toDtoRoleName(Role role);

    @Named("roleNameSet")
    default Set<RoleDTO> toDtoRoleNameSet(Set<Role> role) {
        return role.stream().map(this::toDtoRoleName).collect(Collectors.toSet());
    }
}
