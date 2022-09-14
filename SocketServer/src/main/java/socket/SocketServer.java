package socket;

import org.springframework.stereotype.Service;

@Service
public class SocketServer {
    public void start() {
        new ServerListener(9999).start();
    }
}
