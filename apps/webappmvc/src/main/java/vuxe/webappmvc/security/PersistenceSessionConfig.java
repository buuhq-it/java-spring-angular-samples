package vuxe.webappmvc.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.SpringSessionTransactionManager;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession
public class PersistenceSessionConfig {

    @Bean
    @SpringSessionDataSource
    public DataSource persistencSessionDataSource(@Qualifier("managementDataSource") DataSource ds) {
        return ds;
    }

    @Bean
    @SpringSessionTransactionManager
    public TransactionManager persistencSessionTransactionManager(@Qualifier("managementTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return platformTransactionManager;
    }

    @Bean
    public DataSourceInitializer managementSessionInitializer(
            @Qualifier("managementDataSource") DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("org/springframework/session/jdbc/schema-postgresql.sql"));
        populator.setContinueOnError(true);
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

}
