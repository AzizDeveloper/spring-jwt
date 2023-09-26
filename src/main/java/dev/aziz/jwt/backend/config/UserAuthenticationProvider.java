package dev.aziz.jwt.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.aziz.jwt.backend.dtos.UserDto;
import dev.aziz.jwt.backend.entites.Role;
import dev.aziz.jwt.backend.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    public UserAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        List<String> rolesList = user.getRoles().stream().map(Role::getName).toList();

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("roles", rolesList)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        Claim roles = null;
        Set<Role> rolesSet = null;
        Set<String> rolesSetString = null;
        List<String> rolesList = null;

        if (decoded.getClaims().get("roles") != null) {
            roles = decoded.getClaim("roles");
            rolesList = roles.asList(String.class);
            rolesSetString = rolesList.stream().collect(Collectors.toSet());
            System.out.println("rolesList = " + rolesList);
            rolesSet = rolesSetString.stream().map(Role::new).collect(Collectors.toSet());
        } else {
            System.out.println("(Set<Role>) decoded.getClaims().get(\"roles\"); this is NULL!!!");
        }

        UserDto user = UserDto.builder()
                .login(decoded.getSubject())
                .roles(rolesSet)
                .build();

        return new UsernamePasswordAuthenticationToken(user, null,
                rolesSet.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByLogin(decoded.getSubject());
        List<SimpleGrantedAuthority> collection = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, collection);
    }


}
