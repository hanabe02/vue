package kr.co.seoulit.common.config.controller;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import kr.co.seoulit.common.config.auth.dto.SessionUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        // () -> SessionUser 타입 캐스팅을 의미, session.getAttribute("user") 로 가져온 객체를 SessionUser 타입으로 변환
        // session.getAttribute 의 반호나형은 Object 타입, 이때 우리가 원하는 SessionUser 타입으로 형 변환 한거다.
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        System.out.println("로그아웃 요청 받음!");
        session.invalidate(); // 세션 무효화
        System.out.println("세션 삭제 완료!");
        return ResponseEntity.ok().body("Logout successful");
    }
}
