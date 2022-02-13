package first.iteration.endlesscreation.dto;

import java.util.Set;

public class UserSendDTO {
    private String username;
    private Long userId;
    private String userImageLink;

    public UserSendDTO() {
    }

    public UserSendDTO(String username, Long userId, String userImageLink) {
        this.username = username;
        this.userId = userId;
        this.userImageLink = userImageLink;
    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
