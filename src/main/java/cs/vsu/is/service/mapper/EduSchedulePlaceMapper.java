package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.service.dto.EduSchedulePlaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EduSchedulePlace} and its DTO {@link EduSchedulePlaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface EduSchedulePlaceMapper extends EntityMapper<EduSchedulePlaceDTO, EduSchedulePlace> {}
