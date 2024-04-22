package co.edu.escuelaing.cvds.lab7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import co.edu.escuelaing.cvds.lab7.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService authService;

    @GetMapping("/loginVault")
    public String loginPage() {
        return "loginVault"; // Devuelve la vista de inicio de sesión (login.html)
    }

    @PostMapping("/loginVault")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        if (authService.authenticate(username, password)) {
            // Redirige a la página principal después del inicio de sesión exitoso
            return "redirect:/";
        } else {
            // Si la autenticación falla, agrega un mensaje de error y vuelve a cargar la página de inicio de sesión
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }
}
