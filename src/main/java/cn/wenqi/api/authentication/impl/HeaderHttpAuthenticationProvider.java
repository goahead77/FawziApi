

package cn.wenqi.api.authentication.impl;

import cn.wenqi.api.authentication.AuthenticationException;
import cn.wenqi.api.authentication.support.QuickAuthentication;
import cn.wenqi.api.entity.PlatformUser;
import cn.wenqi.api.service.PlatformUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 头信息验证提供者
 * @author wenqi
 */
@Service
public class HeaderHttpAuthenticationProvider extends AbsHttpAuthenticationProvider {

    private static final Log log = LogFactory.getLog(HeaderHttpAuthenticationProvider.class);

    @Autowired
    private PlatformUserService platformUserService;

    protected PlatformUser doAuth(SecurityContext context, HttpServletRequest request) throws UnsupportedEncodingException, AuthenticationException {
        String userKey = request.getHeader("user-key");
        String userRandom = request.getHeader("user-random");
        String userSecure = request.getHeader("user-secure");
        if (userKey != null && userRandom != null && userSecure != null) {
            log.debug("Header:" + userKey + " " + userRandom + " " + userSecure);
            PlatformUser platformUser = platformUserService.loadUserByUsername(userKey);
            if (platformUser != null) {
                // authorize it
                byte[] toSign = (userKey + userRandom).getBytes("UTF-8");
                byte[] key1 = DigestUtils.md5Digest(toSign);
                byte[] key2 = platformUser.getAppSecureKey();
                byte[] key = new byte[key1.length + key2.length];

                System.arraycopy(key1, 0, key, 0, key1.length);
                System.arraycopy(key2, 0, key, key1.length, key2.length);
                String result = DigestUtils.md5DigestAsHex(key).toLowerCase();
                if (result.equalsIgnoreCase(userSecure)) {
                    //pass
                    if (log.isDebugEnabled())
                        log.debug("User auto login success as " + platformUser.getAppKey());
                    context.setAuthentication(new QuickAuthentication(platformUser));
//                    httpSessionSecurityContextRepository.saveContext(context, holder.getRequest(), holder.getResponse());
                    return platformUser;
                } else {
                    throw new AuthenticationException("bad _user_secure");
//                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "_user_secure not acceptable.");
//                    return;
                }
            } else {
//                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "_user_key invalid.");
//                return;
                throw new AuthenticationException("can not find user:" + userKey);
            }
            // 用户提交了登录请求 而登录请求无法完成 用户需要知道
        } else if (log.isDebugEnabled()) {
            log.debug("User has no plan to log in.");
            return null;
        }

        return null;
    }
}
