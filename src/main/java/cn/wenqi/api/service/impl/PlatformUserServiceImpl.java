package cn.wenqi.api.service.impl;

import cn.wenqi.api.entity.PlatformUser;
import cn.wenqi.api.repository.PlatformUserRepository;
import cn.wenqi.api.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wenqi
 */
@Service
public class PlatformUserServiceImpl implements PlatformUserService {

    @Autowired
    private PlatformUserRepository userRepository;

    @Override
    public PlatformUser loadUserByUsername(String s) throws UsernameNotFoundException {
        PlatformUser user=userRepository.findByAppKey(s);
        if(user==null)
            throw new UsernameNotFoundException("用户"+s+"不存在");
        return user;
    }
}
