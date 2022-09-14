package socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import socket.controller.Key;

@SpringBootApplication
public class SocketServerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SocketServerApplication.class, args);
        applicationContext.getBean(Key.class).generateKey();
        applicationContext.getBean(SocketServer.class).start();
    }
}
