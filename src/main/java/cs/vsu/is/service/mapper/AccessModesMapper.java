package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.service.dto.AccessModesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccessModes} and its DTO {@link AccessModesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccessModesMapper extends EntityMapper<AccessModesDTO, AccessModes> {}
