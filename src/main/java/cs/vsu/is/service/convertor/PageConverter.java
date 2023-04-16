package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Pages;
import cs.vsu.is.service.dto.PageDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PageConverter {
  private final ModelMapper modelMapper;

  public PageConverter() {
    this.modelMapper = new ModelMapper();
  }

  public PageDTO toDto(Pages entity) {
    return modelMapper.map(entity, PageDTO.class);
  }

  public Pages toEntity(PageDTO dto) {
    return modelMapper.map(dto, Pages.class);
  }
}
