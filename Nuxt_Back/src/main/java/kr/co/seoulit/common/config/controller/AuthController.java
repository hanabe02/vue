package kr.co.seoulit.common.config.controller;

import kr.co.seoulit.common.config.auth.dto.SessionUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final HttpSession httpSession;

    public AuthController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/user")
    public Optional<SessionUser> getUser() {
        return Optional.ofNullable((SessionUser) httpSession.getAttribute("user"));
    }
}
