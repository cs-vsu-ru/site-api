package cs.vsu.is.service.dto;

import cs.vsu.is.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated = false;
    private String langKey;
    private String imageUrl;
    private String activationKey;
    private String resetKey;
    private Instant resetDate = null;
    private String mainRole;
}
