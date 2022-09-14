package socket;

import socket.manager.ServerManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    private final int port;
    ServerListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            // 创建Socket服务器
            ServerSocket serverSocket = new ServerSocket(port);
            // 死循环 阻塞接收socket消息
            while (true) {
                Socket socket = serverSocket.accept();

                // 新socket连接后 开启一个独立线程并将该线程添加到管理器中
                ChatSocket chatSocket = new ChatSocket(socket);
                chatSocket.start();
                ServerManager.getInstance().add(chatSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
