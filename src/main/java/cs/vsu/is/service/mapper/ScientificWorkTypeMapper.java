package cs.vsu.is.service.mapper;

import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScientificWorkType} and its DTO {@link ScientificWorkTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScientificWorkTypeMapper extends EntityMapper<ScientificWorkTypeDTO, ScientificWorkType> {}
