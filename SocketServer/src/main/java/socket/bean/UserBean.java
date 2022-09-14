package socket.bean;

import lombok.Data;

/**
 * 用户
 */
@Data
public class UserBean {

    /**
     * 用户ID
     */
    private String uid = "";

    /**
     * 昵称
     */
    private String nickname = "";

    /**
     *
     */
    private String publicKey = "";

    public UserBean(String uid, String nickname, String publicKey) {
        this.uid = uid;
        this.nickname = nickname;
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
