package socket;

import com.google.gson.reflect.TypeToken;
import socket.bean.ChatBean;
import socket.bean.MessageBean;
import socket.bean.UserBean;
import socket.manager.ServerManager;
import util.MessageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import static socket.bean.MessageBean.*;
import static util.GsonUtil.GsonUtil;

public class ChatSocket extends Thread {


    private UserBean userBean;
    private final Socket socket;

    private final Vector<Integer> rooms = new Vector<>();

    public ChatSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message) {
        try {
            OutputStream os = socket.getOutputStream();
            // 加密消息
            message = MessageUtil.encrypt(message, userBean.getPublicKey());
            if (message != null) {
                // 发送消息
                os.write((message).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 获取socket的输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            // 阻塞接收来自客户端的消息
            while ((line = br.readLine()) != null) {
                onMessage(line + "\n");
            }
            quitRoom();
            // 关闭输入流
            br.close();
            ServerManager.getInstance().remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quitRoom() {
        ChatBean chatBean = new ChatBean();
        chatBean.setType(QUIT_ROOM);
        chatBean.setUser(userBean);
        ServerManager.getInstance().quitRoom(this, chatBean);
    }

    /**
     * @param message 格式为：AES加密内容(base64)--------------------------RSA加密的AES密钥(base64)--------------------------占位符
     */
    private void onMessage(String message) {
        // 分割字符串
        // s0:AES加密内容(base64)
        // s1:RSA加密的AES密钥(base64)
        String[] s = message.split("--------------------------");
        String json = MessageUtil.decode(s[0], s[1]);
        MessageBean messageBean = GsonUtil(json, MessageBean.class);
        ChatBean chatBean;
        switch (messageBean.getType()) {
            case CONNECT:
                // 上线
                setUserBean(GsonUtil(messageBean.getContent()));
                sendMessage(GsonUtil(new MessageBean<Boolean>(CONNECT, true)));
                // 进入大厅
                ChatBean chat = new ChatBean();
                chat.setUser(userBean);
                chat.setType(MessageBean.ENTER_ROOM);
                ServerManager.getInstance().enterRoom(this, 0, chat);
                break;
            case OFFLINE:
                // 下线
                break;
            case BROADCAST :
                // 广播频道
                chatBean = setChatBean(GsonUtil(messageBean.getContent()));
                ServerManager.getInstance().publish(this, chatBean);
                break;
            case SPECIFY :
                // 房间
                chatBean = setChatBean(GsonUtil(messageBean.getContent()));
                ServerManager.getInstance().publish(this, chatBean.getRid(), chatBean);
                break;
            case PRIVATE :
                // 私聊
                chatBean = setChatBean(GsonUtil(messageBean.getContent()));
                ServerManager.getInstance().publish(this, chatBean.getUid(), chatBean);
                break;
            case ENTER_ROOM:
                // 进入房间
                chatBean = setChatBean(GsonUtil(messageBean.getContent()));
                ServerManager.getInstance().enterRoom(this, chatBean.getRid(), chatBean);
                break;
            case QUIT_ROOM:
                // 退出房间
                chatBean = setChatBean(GsonUtil(messageBean.getContent()));
                ServerManager.getInstance().quitRoom(this, chatBean.getRid(), chatBean);
                break;
            default:
                break;
        }
    }

    public ChatBean setChatBean(String json) {
        Type type = new TypeToken<ChatBean>() {}.getType();
        ChatBean chatBean = GsonUtil(json, type);
        return chatBean;
    }

    public void setUserBean(String json) {
        Type type = new TypeToken<UserBean>() {}.getType();
        UserBean userBean = GsonUtil(json, type);
        System.out.println(userBean.getNickname() + " 上线");
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public Vector<Integer> getRoom() {
        return rooms;
    }
}
