//package cn.com.cgh.config;
//
//import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
//import com.baidu.fsg.uid.impl.CachedUidGenerator;
//import com.baidu.fsg.uid.impl.DefaultUidGenerator;
//import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Configuration
//public class BaseBean {
//    @Bean
//    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
//        return new DisposableWorkerIdAssigner();
//    }
//
//    @Bean(value = "defaultUidGenerator")
//    public DefaultUidGenerator initDefaultUid(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
//        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
//        defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
//        defaultUidGenerator.setTimeBits(30);
//        defaultUidGenerator.setWorkerBits(20);
//        defaultUidGenerator.setSeqBits(13);
//        defaultUidGenerator.setEpochStr(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        return defaultUidGenerator;
//    }
//
//    @Bean(value = "cachedUidGenerator")
//    public CachedUidGenerator initCachedUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
//        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
//        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
//        return cachedUidGenerator;
//    }
//}
