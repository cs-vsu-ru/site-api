package cs.vsu.is.service.dto.store;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.service.dto.AdminUserDTO;
import cs.vsu.is.service.dto.ArticleDTO;
import cs.vsu.is.service.dto.EventDTO;
import cs.vsu.is.service.dto.LessonDTO;
import cs.vsu.is.service.dto.PageDTO;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import cs.vsu.is.service.dto.TeachingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTOStore {

  private Long id;
  private String patronymic;
  @NotNull
  private String post;
  @NotNull
  private String academicTitle;
  @NotNull
  private String academicDegree;
  @NotNull
  private String experience;
  @NotNull
  private String professionalExperience;
  private LocalDate dateOfBirth;
  @NotNull
  private String login;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private String email;
  private String imageUrl;
  private String createdBy;
  private String createdDate;
  private Set<String> authorities;
  private Set<ArticleDTO> articles = new HashSet<>();
  private Set<EventDTO> events = new HashSet<>();
  // private Set<LessonDTO> lessons = new HashSet<>();
  private Set<PageDTO> pages = new HashSet<>();
  private Set<ScientificLeadershipsDTO> scientificLeaderships = new HashSet<>();
  private Set<TeachingDTO> teachings = new HashSet<>();
}
