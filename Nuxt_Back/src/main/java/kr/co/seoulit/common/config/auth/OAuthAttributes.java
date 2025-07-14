package kr.co.seoulit.common.config.auth;

import kr.co.seoulit.common.config.domain.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final String name;
    private final String email;
    private final String picture;

    @Builder
    public OAuthAttributes(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build();
    }
}
