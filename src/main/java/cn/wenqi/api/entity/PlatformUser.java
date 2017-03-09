package cn.wenqi.api.entity;

import cn.wenqi.api.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author wenqi
 */
@Data
@Entity
@Table(name = "PlatformUser", uniqueConstraints = {@UniqueConstraint(columnNames = "appKey")})
public class PlatformUser implements UserDetails,Serializable{

    private static final long serialVersionUID = 5017969930864649255L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(length = 36, nullable = false)
    private String appKey;
    @JsonIgnore
    private byte[] appSecureKey;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @CollectionTable(name = "PlatformUser_authorityList")
    @ElementCollection
    @Column(length = 50)
    private Set<String> authorityList;

    @Transient
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorityList == null || authorityList.isEmpty())
            return Utils.String2GrantedAuthoritySet(Collections.singletonList("ROLE_USER").stream());
        if (!authorityList.contains("ROLE_USER"))
            authorityList.add("ROLE_USER");
        return Utils.String2GrantedAuthoritySet(authorityList.stream());
    }

    @Override
    public String getUsername() {
        return appKey;
    }

    @JsonIgnore
    private boolean enabled = true;
    @JsonIgnore
    private boolean accountNonExpired = true;
    @JsonIgnore
    private boolean accountNonLocked = true;
    @JsonIgnore
    private boolean credentialsNonExpired = true;

}
