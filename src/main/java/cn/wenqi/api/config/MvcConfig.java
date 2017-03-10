package cn.wenqi.api.config;

import cn.wenqi.api.interceptor.AuthorizeInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author wenqi
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    public MvcConfig() {
        super();
    }
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.jsp("/",".jsp");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new AuthorizeInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("/index");
    }
}
