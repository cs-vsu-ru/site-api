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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO {

  private Long id;
  private String patronymic;
  private String post;
  private String academicTitle;
  private String academicDegree;
  private String experience;
  private String professionalExperience;
  private String dateOfBirth;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
  private String imageUrl;
  // private AdminUserDTO user;
  // private Set<ArticleDTO> articles = new HashSet<>();
  // private Set<EventDTO> events = new HashSet<>();
  // private Set<LessonDTO> lessons = new HashSet<>();
  // private Set<PageDTO> pages = new HashSet<>();
  // private Set<ScientificLeadershipsDTO> scientificLeaderships = new
  // HashSet<>();
  private Set<SpecialitiesDTO> specialities = new HashSet<>();
  private String mainRole;
}
