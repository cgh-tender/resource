package cn.com.cgh.Id.config;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BaseBean {
    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }


    @Bean(value ="cachedUidGenerator")
    public CachedUidGenerator initCachedUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner){
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setEpochStr("2022--12-01");
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return cachedUidGenerator;
    }
}
