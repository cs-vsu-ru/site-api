package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.service.dto.SpecialitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialities} and its DTO {@link SpecialitiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecialitiesMapper extends EntityMapper<SpecialitiesDTO, Specialities> {}
