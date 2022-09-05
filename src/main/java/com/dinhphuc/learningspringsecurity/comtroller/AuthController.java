package com.dinhphuc.learningspringsecurity.comtroller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dinhphuc.learningspringsecurity.model.User;
import com.dinhphuc.learningspringsecurity.objects.ResponseObject;
import com.dinhphuc.learningspringsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    IUserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("refreshToken")
    public ResponseEntity<ResponseObject> refreshTokenController() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Algorithm algorithm = Algorithm.HMAC256("vodinhphuc");
        String access_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .withClaim("roles", roles)
                .sign(algorithm);
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        ResponseObject responseObject = new ResponseObject();
        responseObject.setError(HttpStatus.OK.value());
        responseObject.setMessage("Refresh successfully");
        responseObject.setObject(map);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<ResponseObject> loginController(@RequestBody User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());


        try {
            Authentication auth = authenticationManager.authenticate(token);
            org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

            ResponseObject responseObject = new ResponseObject();
            responseObject.setError(HttpStatus.OK.value());
            responseObject.setMessage("Login successfully");
            List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            //set token
            Algorithm algorithm = Algorithm.HMAC256("vodinhphuc");
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 24*60*60*1000))
                    .withClaim("roles", roles)
                    .sign(algorithm);
            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 120L *24*60*60*1000))
                    .withClaim("roles", roles)
                    .sign(algorithm);

            Map<String, String> map = new HashMap<>();

            map.put("access_token", access_token);
            map.put("refresh_token", refresh_token);

            responseObject.setObject(map);

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("register")
    public ResponseEntity<ResponseObject> registerController(@RequestBody User user) {
        ResponseObject responseObject = new ResponseObject();
        User curUser = userService.findUserByUsername(user.getUsername());
        if (curUser != null) {
            responseObject.setError(HttpStatus.BAD_REQUEST.value());
            responseObject.setMessage("Username is existed");
            responseObject.setObject(null);
            return new ResponseEntity(responseObject, HttpStatus.BAD_REQUEST);
        }
        User retUser = userService.saveUser(user);

        if (retUser == null){
            responseObject.setError(HttpStatus.BAD_REQUEST.value());
            responseObject.setMessage("Registration is failed");
            responseObject.setObject(null);
            return new ResponseEntity(responseObject, HttpStatus.BAD_REQUEST);
        }

        else{
            responseObject.setError(HttpStatus.OK.value());
            responseObject.setMessage("Successfully");
            responseObject.setObject(retUser);
            return new ResponseEntity(responseObject, HttpStatus.OK);
        }
    }
}
