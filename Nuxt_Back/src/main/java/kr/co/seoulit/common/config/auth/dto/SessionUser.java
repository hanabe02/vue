package kr.co.seoulit.common.config.auth.dto;

import kr.co.seoulit.common.config.domain.Users;
import java.io.Serializable;

public class SessionUser implements Serializable {
    private Long id;  // ✅ id 필드 추가
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users user) {
        this.id = user.getId();  // ✅ id 저장
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    // ✅ Getter 추가
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
}
