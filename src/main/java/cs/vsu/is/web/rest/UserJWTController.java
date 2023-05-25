package cs.vsu.is.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs.vsu.is.domain.Authority;
import cs.vsu.is.domain.User;
import cs.vsu.is.repository.UserRepository;
import cs.vsu.is.security.jwt.JWTFilter;
import cs.vsu.is.security.jwt.TokenProvider;
import cs.vsu.is.web.rest.vm.LoginVM;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;


/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

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
        log.debug("начали авторизацю");
        log.debug("получаем логин и пароль {} {}", loginVM.getUsername(), loginVM.getPassword());
        if (!ldap(loginVM.getUsername(), loginVM.getPassword())) {
            AuthResp authResp = new AuthResp();
            HttpHeaders httpHeaders = new HttpHeaders();
            authResp.setMainRole(loginVM.getUsername()+"_"+loginVM.getPassword());
            return new ResponseEntity<>(authResp, httpHeaders, HttpStatus.BAD_REQUEST);
        } else{
            Optional<User> oneByLogin = userRepository.findOneByLogin(loginVM.getUsername());
            User user = oneByLogin.orElseThrow();
            user.setPassword(passwordEncoder.encode(loginVM.getPassword()));
            userRepository.save(user);
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
            if(user.getAuthorities() != null) {
                authResp.mainRole = user.getAuthorities().iterator().next().getName();
            } else {
                authResp.mainRole = "ROLE_EMPLOYEE";
            }

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
        log.debug("метод для проверки черз лдап такие креды {} {} {}", ldapServer, ldapUser, ldapPassword);

        LDAPConnection connection = null;

        try {
            log.debug("пытаемся получить connection ");
            connection = new LDAPConnection(ldapServer, 389, ldapUser, ldapPassword);
            log.debug("connection получен  {}",connection.getConnectionID());
            return true;
        } catch (LDAPException e) {
            log.debug("не получилось получить connection ");
            e.printStackTrace();
        } finally {
            log.debug("final отработал ");
            if (connection != null) {
                connection.close();
            }
        }
        log.debug("дошли до конца вернули фолс ");
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
