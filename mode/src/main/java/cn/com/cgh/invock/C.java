package cn.com.cgh.invock;

import org.springframework.stereotype.Service;

@Service
public class C implements Peple {
    @Override
    public void eat(String e) {
        System.out.println("我吃了 :" + e);
    }
}
