package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Lesson;
import cs.vsu.is.domain.Schedule;
import cs.vsu.is.domain.Subject;
import cs.vsu.is.service.dto.EduSchedulePlaceDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.LessonDTO;
import cs.vsu.is.service.dto.ScheduleDTO;
import cs.vsu.is.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lesson} and its DTO {@link LessonDTO}.
 */
@Mapper(componentModel = "spring")
public interface LessonMapper extends EntityMapper<LessonDTO, Lesson> {
    @Mapping(target = "subject", source = "subject", qualifiedByName = "subjectId")
    @Mapping(target = "eduSchedulePlace", source = "eduSchedulePlace", qualifiedByName = "eduSchedulePlaceId")
    @Mapping(target = "schedule", source = "schedule", qualifiedByName = "scheduleId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    LessonDTO toDto(Lesson s);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);

    @Named("eduSchedulePlaceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EduSchedulePlaceDTO toDtoEduSchedulePlaceId(EduSchedulePlace eduSchedulePlace);

    @Named("scheduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScheduleDTO toDtoScheduleId(Schedule schedule);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
