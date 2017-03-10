package cn.wenqi.api.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;

/**
 * @author wenqi
 */
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Principal  principal=request.getUserPrincipal();
        if(principal!=null){//已经认证了
            String timestamp=request.getParameter("timestamp");
            String headTime=request.getHeader("user-random");
            if(timestamp !=null || headTime!=null){
                long time=Long.valueOf(timestamp==null?headTime:timestamp);
                if(new Date().getTime()-time>60*1000){
                    request.logout();
                }else
                    return true;
            }else
                return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
