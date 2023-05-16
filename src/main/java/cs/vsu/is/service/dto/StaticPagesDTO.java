package cs.vsu.is.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StaticPagesDTO {

  private Long id;
  private String contentAbout;
  private String contentEducation;
  private String contentStudents;
  private String contentPartners;
  private String contentConfidential;
  private String contentContacts;
}
