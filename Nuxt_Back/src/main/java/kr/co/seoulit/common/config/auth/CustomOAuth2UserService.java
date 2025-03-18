package kr.co.seoulit.common.config.auth;

import kr.co.seoulit.common.config.domain.Users;
import kr.co.seoulit.common.config.domain.UserRepository;
import kr.co.seoulit.common.config.auth.dto.SessionUser;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);


    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuthAttributes attributes = OAuthAttributes.of(oAuth2User.getAttributes());

        logger.info("Google User Info: {}", oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        logger.info("Extracted User Info - Email: {}, Name: {}, Picture: {}", email, name, picture);

        // 기존 사용자 확인 후 저장
        Optional<Users> existingUser = userRepository.findByEmail(attributes.getEmail());
        Users user;
        if (existingUser.isPresent()) {
            // db 에 이메일 정보가 있는경우 : 데이터를 가져옴
            user = existingUser.get();
        } else {
            // db에 이메일 정보가 없는 경우 : 데이터 추가
            user = userRepository.save(attributes.toEntity());
        }

        // 세션에 사용자 저장
        httpSession.setAttribute("user", new SessionUser(user));

        return oAuth2User;
    }
}
