package by.sushi.main;

import by.sushi.config.ApplicationConfig;

import by.sushi.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class TestController {

    private final ApplicationConfig applicationConfig;
    private final AuthenticationManager authenticationManager;



    public TestController(ApplicationConfig applicationConfig, AuthenticationManager authenticationManager) {
        this.applicationConfig = applicationConfig;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/index")
    public String indexPage(Model model) {

//        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        by.sushi.user.User user = (by.sushi.user.User) applicationConfig.userDetailsService();
//
//        model.addAttribute("user", user);

        return "index";
    }

    @GetMapping("/main")
    public String mainController(Model model) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        by.sushi.user.User user = (by.sushi.user.User) applicationConfig.userDetailsService();

        model.addAttribute("user", user);

        return "main";
    }

    @PostMapping("/do-login")
    public String loginMethod(HttpServletRequest request, @ModelAttribute("user") by.sushi.user.User user) {

        String email = user.getEmail();
        String password = request.getParameter("password");
        Role role = Role.USER;
        Authentication  authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);



        return "main";
    }
}