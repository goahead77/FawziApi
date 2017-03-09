package cn.wenqi.api.filter;

import cn.wenqi.api.authentication.AuthenticationException;
import cn.wenqi.api.authentication.HttpAuthenticationProvider;
import cn.wenqi.api.config.DispatcherInit;
import cn.wenqi.api.entity.PlatformUser;
import cn.wenqi.api.repository.PlatformUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自动认证过滤器
 */
public class AuthorizeFilter implements Filter {

    private static final Log log = LogFactory.getLog(AuthorizeFilter.class);

    private ServletContext servletContext;
    private ApplicationContext applicationContext;
    private PlatformUserRepository platformUserRepository;
    private SecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthorizeFilter(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public AuthorizeFilter() {
        this(null);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("AuthorizeFilter Init.");
        servletContext = filterConfig.getServletContext();
    }

//    private final static String FILTER_APPLIED = FilterChainProxy.class.getName().concat(
//            ".APPLIED");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("AuthorizeFilter is working around.");
        }

        if (applicationContext == null) {
            String key = FrameworkServlet.SERVLET_CONTEXT_PREFIX + DispatcherInit.DEFAULT_SERVLET_NAME;
            applicationContext = (ApplicationContext) servletContext.getAttribute(key);

            if (applicationContext == null) {
                //在测试环境中 servlet 的名字是空的
                applicationContext = (ApplicationContext) servletContext.getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX);
            }

            if (applicationContext == null)
                throw new ServletException("Spring ApplicationContext Required.");
            platformUserRepository = applicationContext.getBean(PlatformUserRepository.class);
        }

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpRequestResponseHolder holder = new HttpRequestResponseHolder((HttpServletRequest) request, (HttpServletResponse) response);
            SecurityContext context = httpSessionSecurityContextRepository.loadContext(holder);

            if (context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                String[] authenticationNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext,HttpAuthenticationProvider.class);
                log.debug("Length of authenticationNames:"+authenticationNames.length);
                for (String name : authenticationNames) {
                    log.debug("Working Authentication on "+name);
                    HttpAuthenticationProvider provider = applicationContext.getBean(name, HttpAuthenticationProvider.class);
                    try {
                        PlatformUser platformUser = provider.authFromHttpRequest(context, holder);
                        if (platformUser != null)
                            break;//成功认证则跳过。
                    } catch (AuthenticationException e) {
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage());
                        return;
                    }
                }
            } else if (log.isDebugEnabled()) {
                log.debug("User Logged In Already.");
            }

        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
