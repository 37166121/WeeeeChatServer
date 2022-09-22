package socket.bean;

import lombok.Data;

/**
 * 消息
 */
@Data
public class MessageBean<T> {

    // 消息类型常量
    /**
     * 连接到服务器
     */
    public static final int CONNECT = 0x0001;
    /**
     * 断开连接
     */
    public static final int OFFLINE = 0x0002;
    /**
     * 广播消息
     */
    public static final int BROADCAST = 0xf001;
    /**
     * 区域广播
     */
    public static final int SPECIFY = 0xf002;
    /**
     * 私有消息
     */
    public static final int PRIVATE = 0xf003;
    /**
     * 进入房间
     */
    public static final int ENTER_ROOM = 0xff01;
    /**
     * 退出房间
     */
    public static final int QUIT_ROOM = 0xff02;
    /**
     * 消息类型
     */
    private int type = PRIVATE;

    /**
     * 消息内容
     */
    private T content;

    public MessageBean(T content) {
        this(PRIVATE, content);
    }

    public MessageBean(int type, T content) {
        this.type = type;
        this.content = content;
    }
}
