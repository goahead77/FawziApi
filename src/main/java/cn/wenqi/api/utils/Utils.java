package cn.wenqi.api.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wenqi
 */
public class Utils {

    public static Set<SimpleGrantedAuthority> String2GrantedAuthoritySet(Stream<String> input) {
        return input.map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public static String createSecure(String userKey,String userRandom,String appSecureKey) throws UnsupportedEncodingException {
        byte[] toSign = (userKey + userRandom).getBytes("UTF-8");
        byte[] key1 = DigestUtils.md5Digest(toSign);
        byte[] key2 = appSecureKey.getBytes();
        byte[] key = new byte[key1.length + key2.length];

        System.arraycopy(key1, 0, key, 0, key1.length);
        System.arraycopy(key2, 0, key, key1.length, key2.length);
        return DigestUtils.md5DigestAsHex(key).toLowerCase();
    }
}
