package cs.vsu.is.service.convertor.update;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.StaticPages;
import cs.vsu.is.service.dto.StaticPagesDTO;

@Component
public class StaticPagesConverterUpdate {
  private final ModelMapper modelMapper;

  public StaticPagesConverterUpdate() {
    this.modelMapper = new ModelMapper();
  }

  public void substitute(StaticPagesDTO dto, StaticPages entity) {
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    StaticPages staticPage = modelMapper.map(dto, StaticPages.class);
    modelMapper.map(staticPage, entity);
  }

  public void toDTO(StaticPages staticPages) {
    modelMapper.map(staticPages, StaticPagesDTO.class);
  }

  public void toEntity(StaticPagesDTO dto) {
    modelMapper.map(dto, StaticPages.class);
  }
}
