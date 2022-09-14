package socket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.rsa.RSAUtil;

@RestController
@RequestMapping(value = "/s/")
public class Key {

    public void generateKey() {
        RSAUtil.generateKeyPair();
    }

    @RequestMapping(value = "getPublicKey")
    public String getPublicKey() {
        return RSAUtil.getPublicToBase64String();
    }

    // @RequestMapping(value = "getPrivateKey")
    // public String getPrivateKey() {
    //     System.out.println(RSAUtil.getPrivate());
    //     return RSAUtil.getPrivate();
    // }
}
