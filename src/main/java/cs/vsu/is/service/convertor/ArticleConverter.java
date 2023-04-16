package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Articles;
import cs.vsu.is.service.dto.ArticleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ArticleConverter {
  private final ModelMapper modelMapper;

  public ArticleConverter() {
    this.modelMapper = new ModelMapper();
    
  }

  public ArticleDTO toDto(Articles entity) {
    return modelMapper.map(entity, ArticleDTO.class);
  }

  public Articles toEntity(ArticleDTO dto) {
    return modelMapper.map(dto, Articles.class);
  }
}
