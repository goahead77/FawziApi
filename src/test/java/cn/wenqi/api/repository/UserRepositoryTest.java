package cn.wenqi.api.repository;

import cn.wenqi.api.config.TestBase;
import cn.wenqi.api.entity.PlatformUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wenqi
 */
public class UserRepositoryTest extends TestBase{

    @Autowired
    private PlatformUserRepository userRepository;

    @Test
    public void  addUserTest(){
        PlatformUser platformUser =new PlatformUser();
        platformUser.setAppKey("test");
        platformUser.setAppSecureKey("test".getBytes());
        platformUser.setPassword("pwd");
        Set<String> auths=new HashSet<>();
        auths.add("ROLE_ADMIN");
        platformUser.setAuthorityList(auths);
        userRepository.save(platformUser);
    }

}
