package cn.com.cgh.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

//@Component
public class TransactionManagementConfigurerBean implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        DataSourceTransactionManager manager = new DataSourceTransactionManager();
//        manager.setDataSource(dataSource);
//        return manager;
        return null;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
//        DatasourceTransactionManager manager = new DAtasourceTransactionManager();
//        manager.setDataSource(dataSource);
//        return manager;
        return null;
    }
}
