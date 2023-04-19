package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link cs.vsu.is.domain.Employee} entity.
 */
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO {

  private Long id;
  private String patronymic;
  private LocalDate dateOfBirth;
  private AdminUserDTO user;
  private Set<ArticleDTO> articles = new HashSet<>();
  private Set<EventDTO> events = new HashSet<>();
  private Set<LessonDTO> lessons = new HashSet<>();
  private Set<PageDTO> pages = new HashSet<>();
  private Set<ScientificLeadershipsDTO> scientificLeaderships = new HashSet<>();
  private Set<TeachingDTO> teachings = new HashSet<>();

}
