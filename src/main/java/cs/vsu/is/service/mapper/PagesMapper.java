package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Pages;
import cs.vsu.is.service.dto.AccessModesDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.PagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pages} and its DTO {@link PagesDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagesMapper extends EntityMapper<PagesDTO, Pages> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "accessModes", source = "accessModes", qualifiedByName = "accessModesId")
    PagesDTO toDto(Pages s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("accessModesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AccessModesDTO toDtoAccessModesId(AccessModes accessModes);
}
