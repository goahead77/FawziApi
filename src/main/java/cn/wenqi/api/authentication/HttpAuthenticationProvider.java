

package cn.wenqi.api.authentication;

import cn.wenqi.api.entity.PlatformUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;

import java.io.UnsupportedEncodingException;

/**
 * @author wenqi
 */
public interface HttpAuthenticationProvider {

    /**
     * 从HTTP请求认证用户。
     *
     * @param context  当前安全上下文
     * @param holder  holder
     * @return 成功认证用户信息或者null表示未提供认证信息
     * @throws UnsupportedEncodingException
     * @throws AuthenticationException      认证失败
     */
    PlatformUser authFromHttpRequest(SecurityContext context, HttpRequestResponseHolder holder) throws UnsupportedEncodingException, AuthenticationException;
}
