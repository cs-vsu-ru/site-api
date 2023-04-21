package cs.vsu.is.service.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccessModeDTO {

  private Long id;
  private String name;
  // private Set<ArticleDTO> articles = new HashSet<>();
  // private Set<EventDTO> events = new HashSet<>();
  // private Set<PageDTO> pages = new HashSet<>();
}
