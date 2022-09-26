package socket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.rsa.RSAUtil;

@RestController
@RequestMapping(value = "/s/")
public class Key {
    /**
     * 生成密钥对
     */
    public void generateKey() {
        RSAUtil.generateKeyPair();
    }

    /**
     * 获取公钥
     * @return
     */
    @RequestMapping(value = "getPublicKey")
    public String getPublicKey() {
        return RSAUtil.getPublicToBase64String();
    }
}
