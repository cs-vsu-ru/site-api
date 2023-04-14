package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Events;
import cs.vsu.is.service.dto.AccessModesDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.EventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Events} and its DTO {@link EventsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventsMapper extends EntityMapper<EventsDTO, Events> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "accessModes", source = "accessModes", qualifiedByName = "accessModesId")
    EventsDTO toDto(Events s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("accessModesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AccessModesDTO toDtoAccessModesId(AccessModes accessModes);
}
