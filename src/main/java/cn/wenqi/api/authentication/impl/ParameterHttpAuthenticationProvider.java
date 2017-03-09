

package cn.wenqi.api.authentication.impl;

import cn.wenqi.api.authentication.AuthenticationException;
import cn.wenqi.api.authentication.support.QuickAuthentication;
import cn.wenqi.api.entity.PlatformUser;
import cn.wenqi.api.service.PlatformUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用参数认证
 * @author wenqi
 */
@Service
public class ParameterHttpAuthenticationProvider extends AbsHttpAuthenticationProvider {

    private static final Log log = LogFactory.getLog(ParameterHttpAuthenticationProvider.class);

    @Autowired
    private PlatformUserService platformUserService;

    @Override
    protected PlatformUser doAuth(SecurityContext context, HttpServletRequest request)
            throws UnsupportedEncodingException, AuthenticationException {
        String appId = request.getParameter("appid");
        if (appId == null)
            return null;
        PlatformUser user = platformUserService.loadUserByUsername(appId);
        if (user == null)
            throw new AuthenticationException("can not find user:" + appId);

        String timestamp = request.getParameter("timestamp");
        if (timestamp == null) {
            throw new AuthenticationException("with null timestamp");
        }

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new AuthenticationException("with null sign");
        }

        List<String> names = new ArrayList<>(request.getParameterMap().keySet());
        names.sort(String::compareTo);

        StringBuilder toSign = new StringBuilder(names.get(0)).append("=").append(request.getParameter(names.get(0)));
        for (int i = 1; i < names.size(); i++) {
            String name = names.get(i);
            String value = request.getParameter(name);
            if (name.toLowerCase().equals("sign") || value == null || value.length() == 0)
                continue;
            toSign.append("&").append(name.toLowerCase())
                    .append("=").append(value);
        }
        //再添加他的安全码进行计算
        toSign.append(new String(Hex.encode(user.getAppSecureKey())));

        log.debug(toSign.toString());

        if (sign.equalsIgnoreCase(DigestUtils.md5DigestAsHex(toSign.toString().getBytes("UTF-8")))) {
            if (log.isDebugEnabled())
                log.debug("User auto login success as " + user.getAppKey());
            context.setAuthentication(new QuickAuthentication(user));
            log.debug("passed auth");
            return user;
        }else {
            log.debug("Except sign:" + DigestUtils.md5DigestAsHex(toSign.toString().getBytes("UTF-8")) + " Actual:" + sign);
            log.trace("toSignString:" + toSign);
            throw new AuthenticationException("sign认证失败");
        }
    }
}
