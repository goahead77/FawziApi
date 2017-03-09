package cn.wenqi.api.config;

import cn.wenqi.api.filter.AuthorizeFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

/**
 * @author wenqi
 */

//@Order(2)//让DispatcherServlet先进行注册
public class SecurityInitConfig extends AbstractSecurityWebApplicationInitializer {

    /**
     * 在使用SpringSecurity时，必要时先在正常Filter之前加入一些自己想要的Filter，比如文件上传相关的MultipartFilter，编码处理CharacterFilter
     * @param servletContext
     */
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        super.beforeSpringSecurityFilterChain(servletContext);
        insertFilters(servletContext,new RequestContextFilter(),new CharacterFilter(),new AuthorizeFilter());
    }

    public SecurityInitConfig(){
        super();
    }
}
