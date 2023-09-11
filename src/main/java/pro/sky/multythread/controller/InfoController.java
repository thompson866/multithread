package pro.sky.multythread.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port:8080}")
    private int port;

    @GetMapping("getPort")
    public int getPort() {
        return port;
    }
}