package cn.wenqi.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wenqi
 */

@Configuration
@ComponentScan({"cn.wenqi.api"})
@Import(value = {MvcConfig.class,JpaConfig.class,SecurityConfig.class})
public class Boot {
}
