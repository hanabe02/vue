package kr.co.seoulit.common.config.auth.dto;

import kr.co.seoulit.common.config.domain.Users;
import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
