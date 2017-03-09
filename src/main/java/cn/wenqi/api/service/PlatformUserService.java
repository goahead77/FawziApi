package cn.wenqi.api.service;

import cn.wenqi.api.entity.PlatformUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wenqi
 */
public interface PlatformUserService extends UserDetailsService {

    @Override
    PlatformUser loadUserByUsername(String s) throws UsernameNotFoundException;
}
