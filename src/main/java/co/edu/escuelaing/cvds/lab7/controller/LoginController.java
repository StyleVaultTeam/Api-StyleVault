package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.*;
import co.edu.escuelaing.cvds.lab7.repository.SessionRepository;
import co.edu.escuelaing.cvds.lab7.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new token("User Not found"));
        } else if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new token("Invalid email or password"));
        } else {
            if (sessionRepository.findByUser(user) != null) {
                sessionRepository.delete(sessionRepository.findByUser(user));
            }
            // Guardar la sesión
            UUID token = UUID.randomUUID();
            Session session = new Session(token, Instant.now(), user);
            sessionRepository.save(session);

            // Crear la respuesta con el token en formato JSON
            token token1 = new token(token.toString());

            // Configurar y agregar la cookie al response
            Cookie cookie = new Cookie("authToken", token.toString());
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setDomain("localwebapp");
            cookie.setPath("/");
            //response.addCookie(cookie);

            ResponseCookie springCookie = ResponseCookie.from("refresh-token", UUID.randomUUID().toString())
                    .sameSite("None")
                    .domain("localwebapp")
                    .httpOnly(true)
                    .build();

            response.addHeader("Set-Cookie", springCookie.toString());

            return ResponseEntity.ok().body(token1);
        }

    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutSubmit(@CookieValue("authToken") UUID token, HttpServletResponse response) {
        sessionRepository.delete(sessionRepository.findByToken(token));
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new name("User already exists"));
        }

        User user = new User(email, password, Arrays.asList(UserRole.CLIENTE)); // Inicialmente el token es null
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username")
    @ResponseBody
    public ResponseEntity<?> getUsernameFromToken(@CookieValue("authToken") UUID token) {
        Session session = sessionRepository.findByToken(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new name("User not found"));
        }

        // Devolver el nombre del usuario
        return ResponseEntity.ok().body(new name(session.getUser().getName()));
    }

    @GetMapping("protected/example")
    public String protectedExample() {
        return "login/protected";
    }
}