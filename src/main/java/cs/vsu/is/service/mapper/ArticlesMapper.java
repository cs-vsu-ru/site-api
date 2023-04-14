package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.domain.Articles;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.service.dto.AccessModesDTO;
import cs.vsu.is.service.dto.ArticlesDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Articles} and its DTO {@link ArticlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticlesMapper extends EntityMapper<ArticlesDTO, Articles> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "accessModes", source = "accessModes", qualifiedByName = "accessModesId")
    ArticlesDTO toDto(Articles s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("accessModesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AccessModesDTO toDtoAccessModesId(AccessModes accessModes);
}
