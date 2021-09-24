//package cn.com.cgh.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//
////@Component
//public class TransactionManagementConfigurerBean implements TransactionManagementConfigurer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        DataSourceTransactionManager manager = new DataSourceTransactionManager();
//        manager.setDataSource(dataSource);
//        return manager;
//    }
//
////    @Bean
////    public PlatformTransactionManager platformTransactionManager(){
////        DataSourceTransactionManager manager = new DataSourceTransactionManager();
////        manager.setDataSource(dataSource);
////        return manager;
////    }
//}