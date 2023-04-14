package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.Students;
import cs.vsu.is.service.dto.StudentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Students} and its DTO {@link StudentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentsMapper extends EntityMapper<StudentsDTO, Students> {}
