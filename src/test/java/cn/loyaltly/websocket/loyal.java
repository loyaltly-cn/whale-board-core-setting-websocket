package cn.loyaltly.websocket;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class loyal {

    private final Set<Integer> list = new HashSet<>();

    @Test
    public void set(){

        list.add(1);
        list.add(2);
        System.out.println(list);
    }
}
