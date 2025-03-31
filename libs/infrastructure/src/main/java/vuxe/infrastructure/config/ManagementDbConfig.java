package vuxe.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "vuxe.infrastructure.management.repository",
        entityManagerFactoryRef = "managementEntityManagerFactory",
        transactionManagerRef = "managementTransactionManager"
)
@EntityScan(basePackages = {
        "vuxe.domain.management.entity",
        "vuxe.domain.common.entity"
})
public class ManagementDbConfig {
    private final Environment environment;

    public ManagementDbConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "managementDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.management")
    public DataSource managementDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "managementEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean managementEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("managementDataSource") DataSource dataSource
    ) {
        Map<String, Object> jpaProperties = new HashMap<>();
//        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
//        jpaProperties.put("hibernate.format_sql", true);
//        jpaProperties.put("hibernate.use_sql_comments", true);
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa-management.properties.hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.format_sql", environment.getProperty("spring.jpa-management.properties.hibernate.format_sql"));
        jpaProperties.put("hibernate.use_sql_comments", environment.getProperty("spring.jpa-management.properties.hibernate.use_sql_comments"));


        return builder
                .dataSource(dataSource)
                .packages("vuxe.domain.management.entity", "vuxe.domain.common.entity")
                .persistenceUnit("management")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name = "managementTransactionManager")
    public PlatformTransactionManager managementTransactionManager(
            @Qualifier("managementEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
