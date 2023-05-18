package cs.vsu.is.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * A EduSchedulePlace.
 */
@Entity
@Table(name = "edu_schedule_place")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class EduSchedulePlace implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "is_denominator")
  private Boolean isDenominator;

  @Column(name = "start_time")
  private String startTime;

  @Column(name = "end_time")
  private String endTime;

  @Column(name = "day_of_weak")
  private Integer dayOfWeak;

}
