package cs.vsu.is.service.dto;

import cs.vsu.is.config.Constants;
import cs.vsu.is.domain.Authority;
import cs.vsu.is.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long id;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
  private String imageUrl;
  private boolean activated = false;
  private String langKey;
  private String createdBy;
  private Instant createdDate;
  private String lastModifiedBy;
  private Instant lastModifiedDate;
  private Set<String> authorities;

  public AdminUserDTO(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.activated = user.isActivated();
    this.imageUrl = user.getImageUrl();
    this.langKey = user.getLangKey();
    this.createdBy = user.getCreatedBy();
    this.createdDate = user.getCreatedDate();
    this.lastModifiedBy = user.getLastModifiedBy();
    this.lastModifiedDate = user.getLastModifiedDate();
    this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
  }
}
