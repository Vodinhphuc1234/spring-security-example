package com.dinhphuc.learningspringsecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dinhphuc.learningspringsecurity.model.User;
import com.dinhphuc.learningspringsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("AUTHORIZATION");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] infos = authorizationHeader.split(" ");
        if (infos.length != 2 || !infos[0].equals("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String access_token = infos[1];
        Algorithm algorithm = Algorithm.HMAC256("vodinhphuc");
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(access_token);
//            List<String> roles = (List<String>) decodedJWT.getClaims().get("roles");
            String username = decodedJWT.getSubject();
            User user = userRepository.findUsersByUsername(username);
            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception exception) {
            filterChain.doFilter(request, response);
        }
    }
}
