package cs.vsu.is.service.dto.store;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
  private Set<String> authorities;
  private Set<Integer> specialitiesIds = new HashSet<>();
  private Set<Integer> subjectIds = new HashSet<>();
}
