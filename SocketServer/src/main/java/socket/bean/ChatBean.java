package socket.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ChatBean {

    /**
     * 消息内容
     */
    private String content = "";

    /**
     * 发送方
     */
    private UserBean user = new UserBean("", "", "");

    /**
     * 接收方 私聊 用户id
     */
    private String toUid = "";

    /**
     * 接收方 群聊 房间id
     */
    private int toRid = 0;

    /**
     * 消息发布时间
     */
    private Long time = new Date().getTime();
}
