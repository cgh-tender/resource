package cn.com.cgh.mybatisTest;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;

public class MyMybatisTest {
    @Resource
    private SqlSessionFactoryBuilder factory;

    public void test(){
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = factory.build(reader).openSession();
        Object mapper = sqlSession.getMapper(null);
        mapper.getClass();

    }
}
