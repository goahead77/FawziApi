

package cn.wenqi.api.authentication.support;

import cn.wenqi.api.entity.PlatformUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author wenqi
 */
public class QuickAuthentication implements Authentication {

    private final PlatformUser platformUser;

    public QuickAuthentication(PlatformUser platformUser) {
        this.platformUser = platformUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return platformUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return platformUser;
    }

    @Override
    public Object getPrincipal() {
        return platformUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
    }

    @Override
    public String getName() {
        return platformUser.getAppKey();
    }
}
