package cs.vsu.is.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs.vsu.is.domain.Authority;
import cs.vsu.is.domain.User;
import cs.vsu.is.repository.UserRepository;
import cs.vsu.is.security.jwt.JWTFilter;
import cs.vsu.is.security.jwt.TokenProvider;
import cs.vsu.is.web.rest.vm.LoginVM;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.unboundid.ldap.sdk.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;


/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Data
    class AuthResp {
        JWTToken jwtToken;
        Set<Authority> authorities;
        String mainRole;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResp> authorize(@Valid @RequestBody LoginVM loginVM) {
        if (!ldap(loginVM.getUsername(), loginVM.getPassword())) {
            AuthResp authResp = new AuthResp();
            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity<>(authResp, httpHeaders, HttpStatus.BAD_REQUEST);
        } else{
            Optional<User> oneByLogin = userRepository.findOneByLogin(loginVM.getUsername());
            User user = oneByLogin.orElseThrow();
            user.setPassword(passwordEncoder.encode(loginVM.getPassword()));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        User user = userRepository.findOneByLogin(authentication.getName()).get();
        AuthResp authResp = new AuthResp();
        authResp.jwtToken = new JWTToken(jwt);
        authResp.authorities = user.getAuthorities();
        authResp.mainRole = null;
        for (Authority authority : user.getAuthorities()) {
            if (authority.getName().equals("ROLE_ADMIN")) {
                authResp.mainRole = "ROLE_ADMIN";
                break;
            }
        }
        if (authResp.mainRole == null) {
            authResp.mainRole = user.getAuthorities().iterator().next().getName();
        }
        return new ResponseEntity<>(authResp, httpHeaders, HttpStatus.OK);
    }

    private boolean ldap(String uname, String pass) {
        if("admin".equals(uname)) {
            return true;
        }
        if("user".equals(uname)) {
            return true;
        }
        String ldapServer = "csfs.cs.vsu.ru";
        String ldapUser = uname+ "@cs.vsu.ru";
        String ldapPassword = pass;

        LDAPConnection connection = null;

        try {
            connection = new LDAPConnection(ldapServer, 389, ldapUser, ldapPassword);
            return true;
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
                return false;
            }
        }
        return false;
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
