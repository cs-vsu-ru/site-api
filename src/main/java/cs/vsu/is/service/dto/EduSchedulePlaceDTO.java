package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EduSchedulePlaceDTO {

  private Long id;

  private Boolean isDenominator;

  private Instant startTime;

  private Instant endTime;

  private Integer dayOfWeak;

}
