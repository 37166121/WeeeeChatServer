package socket.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RoomBean {
    /**
     * 房间类型常量
     */
    static final int ROOM = 0x0001;
    static final int PRIVATE = 0x0002;
    /**
     * 房间id
     */
    private int rid = 0;
    /**
     * 房间名称
     */
    private String name = "";
    /**
     * 房间人数
     */
    private int count = 0;
    /**
     * 房间中产生的消息
     */
    private ArrayList<ChatBean> messages = new ArrayList<>();
    /**
     * 房间类型
     */
    private int type = ROOM;
    /**
     * 是否能进入房间
     */
    private Boolean isEnter = true;

    public RoomBean() {}

    public RoomBean(int rid) {
        this(rid, String.valueOf(rid));
    }

    public RoomBean(int rid, String name) {
        this.rid = rid;
        this.name = name;
    }
}
