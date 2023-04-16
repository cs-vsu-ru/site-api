package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduleDTO implements Serializable {

  private Long id;

  private String name;

  private Instant uploadingTime;

  private Boolean isActual;
}
