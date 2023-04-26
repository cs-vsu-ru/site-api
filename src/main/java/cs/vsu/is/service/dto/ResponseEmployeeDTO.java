package cs.vsu.is.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ResponseEmployeeDTO {

    private Long id;
    private String patronymic;
    private String post;
    private String academicTitle;
    private String academicDegree;
    private String experience;
    private String professionalExperience;
    private LocalDate dateOfBirth;
    private AdminUserDTO user;
    private Set<ArticleDTO> articles = new HashSet<>();
    private Set<EventDTO> events = new HashSet<>();
    private ResponseLessonsDTO lessons;
    private Set<PageDTO> pages = new HashSet<>();
    private Set<ScientificLeadershipsDTO> scientificLeaderships = new HashSet<>();
    private Set<TeachingDTO> teachings = new HashSet<>();
    private String mainRole;
}
