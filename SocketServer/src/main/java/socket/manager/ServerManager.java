package socket.manager;

import socket.ChatSocket;
import socket.bean.ChatBean;
import socket.bean.MessageBean;
import socket.bean.RoomBean;
import socket.bean.UserBean;
import socket.controller.Room;

import static util.GsonUtil.GsonUtil;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerManager {
    private ServerManager() {}
    private static final ServerManager sm = new ServerManager();
    public static ServerManager getInstance() {
        return sm;
    }
    // 定义一个集合，用来存放不同的客户端
    Vector<ChatSocket> vector = new Vector<ChatSocket>();

    public Vector<ChatSocket> getVector() {
        return vector;
    }

    /**
     * 将socket添加到集合中
     * @param cs
     */
    public void add(ChatSocket cs) {
        vector.add(cs);
    }

    /**
     * 客户端断开
     * @param cs 客户端socket
     */
    public void remove(ChatSocket cs) {
        System.out.println(cs.getUserBean().getNickname() + " 下线");
        vector.remove(cs);
    }

    /**
     * 广播消息
     * @param socket 消息来源客户端
     * @param chatBean 消息
     */
    public void publish(ChatSocket socket, ChatBean chatBean) {
        vector.forEach( it -> {
            if (!socket.equals(it)) {
                it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.BROADCAST, chatBean)));
            }
        });
    }

    /**
     * 群聊
     * @param socket 消息来源客户端
     * @param rid 房间号
     * @param chatBean 消息
     */
    public void publish(ChatSocket socket, int rid, ChatBean chatBean) {
        vector.forEach( it -> {
            if (!socket.equals(it)) {
                it.getRoom().forEach( action -> {
                    if (action == rid) {
                        it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.SPECIFY, chatBean)));
                    }
                });
            }
        });
    }

    /**
     * 进入房间
     * @param socket 消息来源客户端
     * @param rid 房间号
     * @param chatBean 消息
     */
    public void enterRoom(ChatSocket socket, int rid, ChatBean chatBean) {
        socket.getRoom().forEach( action -> {
            if (action == rid) {
                return;
            }
        });
        RoomBean roomBean = new RoomBean(rid);
        vector.forEach( it -> {
            if (socket.equals(it)) {
                // 把房间id添加到列表中 并返回房间人数
                socket.getRoom().add(rid);
                if (rid != 0) {
                    roomBean.setCount(new Room().getRoom(rid));
                    it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.ENTER_ROOM, roomBean)));
                }
            } else {
                it.getRoom().forEach( action -> {
                    if (action == rid) {
                        roomBean.getMessages().add(chatBean);
                        it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.ENTER_ROOM, roomBean)));
                    }
                });
            }
        });
    }

    /**
     * 退出房间
     * @param socket 消息来源客户端
     * @param rid 房间号
     * @param chatBean 消息
     */
    public void quitRoom(ChatSocket socket, int rid, ChatBean chatBean) {
        vector.forEach( it -> {
            if (socket.equals(it)) {
                socket.getRoom().remove((Integer)rid);
            } else {
                it.getRoom().forEach( action -> {
                    if (action == rid) {
                        it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.QUIT_ROOM, chatBean)));
                    }
                });
            }
        });
    }

    public void quitRoom(ChatSocket socket, ChatBean chatBean) {
        socket.getRoom().forEach( room -> {
            vector.forEach( it -> {
                if (!socket.equals(it)) {
                    it.getRoom().forEach( rid -> {
                        if (Objects.equals(rid, room)) {
                            chatBean.setRid(rid);
                            it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.QUIT_ROOM, chatBean)));
                        }
                    });
                }
            });
        });
    }

    /**
     * 私聊
     * @param socket 消息来源客户端
     * @param uid 对方用户id
     * @param chatBean 消息
     */
    public void publish(ChatSocket socket, String uid, ChatBean chatBean) {
        vector.forEach( it -> {
            if (!socket.equals(it) && it.getUserBean().getUid().equals(uid)) {
                it.sendMessage(GsonUtil(new MessageBean<>(MessageBean.PRIVATE, chatBean)));
            }
        });
    }
}
