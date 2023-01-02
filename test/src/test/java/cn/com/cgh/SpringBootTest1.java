package cn.com.cgh;

import cn.com.cgh.config.BaseBeanCon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTest1 {
    @Autowired
    public BaseBeanCon baseBeanCon;

    @Test
    public void setRuntimeService() {
        System.out.println(1111);
        System.out.println(baseBeanCon.getdWCacheType());
    }
}
