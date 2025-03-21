package kr.co.seoulit.common.config;

import kr.co.seoulit.common.config.auth.CustomOAuth2UserService;
import kr.co.seoulit.common.config.auth.dto.SessionUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정 추가
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeRequests(auth -> auth
                        // .antMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                        // .anyRequest().authenticated()
                                // 🔓 OPTIONS (preflight) 요청 허용
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                // 🔓 모든 API 인증 없이 허용 (우선 전체 열어두기)
                                .antMatchers("/**").permitAll()

                        // 🔒 특정 API만 나중에 인증 필요하면 추가
                        // .antMatchers("/auth/logout", "/auth/me").authenticated()

                        // 🔒 그 외는 막기 (지금은 필요 없음)
                        // .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            // ✅ 세션에서 사용자 정보 확인
                            SessionUser user = (SessionUser) request.getSession().getAttribute("user");

                            if (user != null) {
                                System.out.println("✅ 로그인 성공: 세션에 사용자 정보 저장됨 → " + user);
                            } else {
                                System.out.println("🚨 로그인 성공했지만 세션에 사용자 정보 없음!");
                            }

                            // ✅ 프론트엔드의 `/auth-callback` 페이지로 리디렉션
                            response.sendRedirect("http://localhost:3000/auth-callback");
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .formLogin(form -> form.disable()) // 기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // Basic Auth 비활성화

        return http.build();
    }

    // ✅ CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // ✅ Vue 프론트엔드 허용
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}



