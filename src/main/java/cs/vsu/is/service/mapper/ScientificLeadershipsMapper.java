package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.domain.Students;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
import cs.vsu.is.service.dto.StudentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScientificLeaderships} and its DTO {@link ScientificLeadershipsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScientificLeadershipsMapper extends EntityMapper<ScientificLeadershipsDTO, ScientificLeaderships> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentsId")
    @Mapping(target = "scientificWorkType", source = "scientificWorkType", qualifiedByName = "scientificWorkTypeId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    ScientificLeadershipsDTO toDto(ScientificLeaderships s);

    @Named("studentsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StudentsDTO toDtoStudentsId(Students students);

    @Named("scientificWorkTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScientificWorkTypeDTO toDtoScientificWorkTypeId(ScientificWorkType scientificWorkType);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
