package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Specialities;
import cs.vsu.is.domain.Subject;
import cs.vsu.is.domain.Teaching;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.SpecialitiesDTO;
import cs.vsu.is.service.dto.SubjectDTO;
import cs.vsu.is.service.dto.TeachingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teaching} and its DTO {@link TeachingDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeachingMapper extends EntityMapper<TeachingDTO, Teaching> {
    @Mapping(target = "subject", source = "subject", qualifiedByName = "subjectId")
    @Mapping(target = "specialities", source = "specialities", qualifiedByName = "specialitiesId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    TeachingDTO toDto(Teaching s);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);

    @Named("specialitiesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SpecialitiesDTO toDtoSpecialitiesId(Specialities specialities);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
