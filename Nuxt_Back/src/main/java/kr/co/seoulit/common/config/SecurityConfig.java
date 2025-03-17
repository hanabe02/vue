package kr.co.seoulit.common.config;

import kr.co.seoulit.common.config.auth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
        logger.info("✅ SecurityConfig 생성자 실행됨");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요 시)
                .authorizeRequests(auth -> auth
                        .antMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:3000", true) // 🔥 로그인 성공 후 Vue로 리디렉션
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .formLogin(form -> form.disable()) // ❗ 기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // ❗ Basic Auth 비활성화

        logger.info("✅ /oauth2/authorization/google 요청을 처리함");

        return http.build();
    }
}
