package socket.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ChatBean {

    /**
     * 消息内容
     */
    private int type = MessageBean.SPECIFY;

    /**
     * 消息内容
     */
    private String content = "";
    /**
     * 发送方id
     */
    private String fromUid = "";
    /**
     * 发送方昵称
     */
    private String nickname = "";

    /**
     * 接收方 私聊 用户id
     */
    private String uid = "";

    /**
     * 接收方 群聊 房间id
     */
    private int rid = 0;

    /**
     * 消息发布时间
     */
    private Long time = new Date().getTime();

    public ChatBean() {}

    public ChatBean(String fromUid, String nickname) {
        this.fromUid = fromUid;
        this.nickname = nickname;
    }
}
