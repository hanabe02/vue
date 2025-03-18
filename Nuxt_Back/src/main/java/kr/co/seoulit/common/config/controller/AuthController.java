package kr.co.seoulit.common.config.controller;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import kr.co.seoulit.common.config.auth.dto.SessionUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final HttpSession httpSession;

    public AuthController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    //    @GetMapping("/user")
//    public Optional<SessionUser> getUser() {
//        return Optional.ofNullable((SessionUser) httpSession.getAttribute("user"));
//    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        System.out.println("\n"+ user+"\n");

        if (user == null) {
            System.out.println("🚨 세션에 사용자 정보 없음!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        System.out.println("\n" + "✅ 세션에서 가져온 사용자: " + user+ "\n");


        // JSON 형태로 응답
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("picture", user.getPicture());

        System.out.println("\n" + "✅ 세션에서 가져온 사용자: " + response+ "\n");

        return ResponseEntity.ok(response);
    }
}
