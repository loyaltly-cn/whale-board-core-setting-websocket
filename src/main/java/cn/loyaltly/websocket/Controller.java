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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/loyal")
public class Controller {

    private Session session;
    private static final Map<String, Set<Session>> map = new ConcurrentHashMap<>();
    @OnOpen
    public void connect(Session session){
        if (!map.containsKey("loyal")){
            Set<Session> sion = new HashSet<>();
            sion.add(session);
            map.put("loyal",sion);
        }else {
            map.get("loyal").add(session);
        }

        try {
            session.getBasicRemote().sendText("connect successful");
        }catch (IOException i){
//            i.printStackTrace();
        }
    }

    @OnMessage
    public void message(byte[] msg){
        map.get("loyal").forEach(data ->{
            try {
                ByteBuffer buffer = ByteBuffer.wrap(msg);
                data.getBasicRemote().sendBinary(buffer);
            }catch (IOException i){
//                i.printStackTrace();
            }
        });
    }

    @OnClose
    public void close(){
        System.out.println("close");
    }
}