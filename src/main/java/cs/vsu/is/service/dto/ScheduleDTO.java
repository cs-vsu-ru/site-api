package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduleDTO {

  private Long id;

  private String name;

  private Instant uploadingTime;

  private Boolean isActual;
}
