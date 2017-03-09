package cn.wenqi.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wenqi
 */
@Configuration
@ImportResource({"classpath:spring_db_prod.xml","classpath:spring_db_dev.xml"})
@EnableJpaRepositories(basePackages = {"cn.wenqi.api.repository"})
public class JpaConfig {
}
