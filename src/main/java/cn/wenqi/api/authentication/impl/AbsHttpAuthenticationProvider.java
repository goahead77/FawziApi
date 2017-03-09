

package cn.wenqi.api.authentication.impl;

import cn.wenqi.api.authentication.AuthenticationException;
import cn.wenqi.api.authentication.HttpAuthenticationProvider;
import cn.wenqi.api.entity.PlatformUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author wenqi
 */
public abstract class AbsHttpAuthenticationProvider implements HttpAuthenticationProvider {

    private static final Log log = LogFactory.getLog(AbsHttpAuthenticationProvider.class);

    private SecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();

    /**
     * 实际的认证者，如果成功返回认证用户必须调用{@link SecurityContext#setAuthentication(Authentication)}
     *
     * @param context 认证上下文
     * @param request HTTP请求
     * @return 成功认证的用户 可以为null表示没有认证信息
     * @throws UnsupportedEncodingException
     * @throws AuthenticationException      认证失败异常
     */
    protected abstract PlatformUser doAuth(SecurityContext context, HttpServletRequest request) throws UnsupportedEncodingException, AuthenticationException, UsernameNotFoundException;

    @Override
    public PlatformUser authFromHttpRequest(SecurityContext context, HttpRequestResponseHolder holder) throws UnsupportedEncodingException, AuthenticationException {
        try {
            PlatformUser user = doAuth(context, holder.getRequest());
            httpSessionSecurityContextRepository.saveContext(context, holder.getRequest(), holder.getResponse());
            return user;
        } catch (UsernameNotFoundException ex) {
            throw new AuthenticationException("can not find user:" + ex.getLocalizedMessage());
        }
    }
}
