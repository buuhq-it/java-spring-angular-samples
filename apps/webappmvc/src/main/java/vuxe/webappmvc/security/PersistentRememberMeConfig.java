package vuxe.webappmvc.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class PersistentRememberMeConfig {

    @Bean
    public PersistentTokenRepository persistentTokenRepository(@Qualifier("managementDataSource") DataSource managementDataSource) {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(managementDataSource);

        return repo;
    }
}
