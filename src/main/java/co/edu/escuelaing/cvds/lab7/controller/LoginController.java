package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Session;
import co.edu.escuelaing.cvds.lab7.model.User;
import co.edu.escuelaing.cvds.lab7.model.UserRole;
import co.edu.escuelaing.cvds.lab7.repository.SessionRepository;
import co.edu.escuelaing.cvds.lab7.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public LoginController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> loginSubmit(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email or password");
        }

        Session session = new Session(UUID.randomUUID(), Instant.now(), user);
        sessionRepository.save(session);

        String responseBody = "{\"authToken\":\"" + session.getToken().toString() + "\"}";

        Cookie cookie = new Cookie("authToken", session.getToken().toString());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutSubmit(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<?> registerSubmit(@RequestBody Map<String, String> registrationInfo) {
        String email = registrationInfo.get("email");
        String password = registrationInfo.get("password");

        if (userRepository.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        User user = new User(email, password, Arrays.asList(UserRole.CLIENTE));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("protected/example")
    public String protectedExample() {
        return "login/protected";
    }
    @GetMapping("/api/login/{token}")
    @ResponseBody
    public ResponseEntity<?> getUsernameFromToken(@PathVariable String token) {
        Session session = sessionRepository.findByToken(UUID.fromString(token));
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found");
        }


        User user = session.getUser();

        return ResponseEntity.ok().body(user.getName());
    }
}
