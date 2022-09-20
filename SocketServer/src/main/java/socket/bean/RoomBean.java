package socket.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RoomBean {
    static final int ROOM = 0x0001;
    static final int PRIVATE = 0x0002;

    private int rid = 0;
    private String name = "";
    private int count = 0;
    private ArrayList<ChatBean> messages = new ArrayList<>();
    private int type = ROOM;

    public RoomBean() {}

    public RoomBean(int rid) {
        this.rid = rid;
        name = String.valueOf(rid);
    }

    public RoomBean(int rid, String name) {
        this.rid = rid;
        this.name = name;
    }
}
