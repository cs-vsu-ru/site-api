package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EduSchedulePlaceDTO implements Serializable {

  private Long id;

  private Boolean isDenominator;

  private Instant startTime;

  private Instant endTime;

  private Integer dayOfWeak;

}
