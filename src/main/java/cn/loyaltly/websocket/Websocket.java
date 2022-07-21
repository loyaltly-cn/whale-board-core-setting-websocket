package cn.loyaltly.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@ServerEndpoint(value = "/loyal")
public class Websocket {

    private static final Set<Session> list = new HashSet<>();

    @OnOpen
    public void connect(Session session){

        list.add(session);
        System.out.println("open -> current size:"+list.size()+" value:"+list);
    }

    @OnMessage
    public void message(byte[] msg){
        list.forEach(s ->{
            try {
                s.getBasicRemote().sendBinary(ByteBuffer.wrap(msg));
                System.out.println("onMessage:"+ Arrays.toString(msg));
            }catch (IOException i){
                i.printStackTrace();
            }
        });
    }

    @OnClose
    public void close(Session session){
        list.remove(session);
        System.out.println("close -> current size:"+list.size()+" value:"+list);
    }
}