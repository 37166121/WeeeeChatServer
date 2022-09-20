package socket.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socket.manager.ServerManager;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "/room/")
public class Room {

    @RequestMapping(value = "getRoom/{rid}")
    public int getRoom(@PathVariable int rid) {
        AtomicInteger count = new AtomicInteger();
        ServerManager.getInstance().getVector().forEach( it -> {
            it.getRoom().forEach( room -> {
                if (room == rid) {
                    count.getAndIncrement();
                }
            });
        });
        return count.get();
    }
}
