package vuxe.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "vuxe.infrastructure.ordering.repository",
        entityManagerFactoryRef = "orderingEntityManagerFactory",
        transactionManagerRef = "orderingTransactionManager"
)
@EntityScan(basePackages = {
        "vuxe.domain.ordering.entity",
        "vuxe.domain.common.entity"
})
public class OrderingDbConfig  {
    private final Environment environment;

    public OrderingDbConfig(Environment environment) {
        this.environment = environment;
    }

    @Primary
    @Bean(name = "orderingDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ordering")
    public DataSource orderingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "orderingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean orderingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("orderingDataSource") DataSource dataSource
    ) {
        Map<String, Object> jpaProperties = new HashMap<>();
//        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        jpaProperties.put("hibernate.format_sql", true);
//        jpaProperties.put("hibernate.use_sql_comments", true);

        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa-ordering.properties.hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.format_sql", environment.getProperty("spring.jpa-ordering.properties.hibernate.format_sql"));
        jpaProperties.put("hibernate.use_sql_comments", environment.getProperty("spring.jpa-ordering.properties.hibernate.use_sql_comments"));

        return builder
                .dataSource(dataSource)
                .packages("vuxe.domain.ordering.entity", "vuxe.domain.common.entity")
                .persistenceUnit("ordering")
                .properties(jpaProperties)
                .build();
    }
    @Primary
    @Bean(name = "orderingTransactionManager")
    public PlatformTransactionManager orderingTransactionManager(
            @Qualifier("orderingEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
