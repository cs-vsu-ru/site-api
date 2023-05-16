package cs.vsu.is.service.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cs.vsu.is.domain.Slider;
import cs.vsu.is.service.dto.SliderDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SliderConverter {
  private final ModelMapper modelMapper;

  public SliderConverter() {
    this.modelMapper = new ModelMapper();

  }

  public SliderDTO toDto(Slider entity) {
    return modelMapper.map(entity, SliderDTO.class);
  }

  public Slider toEntity(SliderDTO dto) {
    return modelMapper.map(dto, Slider.class);
  }
}
