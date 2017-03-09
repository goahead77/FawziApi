package cn.wenqi.api.repository;

import cn.wenqi.api.entity.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wenqi
 */
@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUser,Long> {
    PlatformUser findByAppKey(String s);
}
