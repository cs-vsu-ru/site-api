package cs.vsu.is.service.dto.update;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTOUpdate {

  private Long id;
  private String patronymic;
  private String post;
  private String academicTitle;
  private String academicDegree;
  private String experience;
  private String professionalExperience;
  private LocalDate dateOfBirth;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
  private String imageUrl;
  private String createdBy;
  private String createdDate;
  private String mainRole;
  private String plan;
  // private Set<LessonDTO> lessons = new HashSet<>();
  // private Set<TeachingDTO> teachings = new HashSet<>();

}
