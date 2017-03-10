package cn.wenqi.api.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wenqi
 */
public class Utils {

    public static Set<SimpleGrantedAuthority> String2GrantedAuthoritySet(Stream<String> input) {
        return input.map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    /**
     * 用于在head进行校验的签名
     * @param userKey 给用户的appKey
     * @param userRandom 当前时间戳{@link System#currentTimeMillis()}
     * @param appSecureKey 用户的安全码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String headSecure(String userKey, String userRandom, String appSecureKey) throws UnsupportedEncodingException {
        byte[] toSign = (userKey + userRandom).getBytes("UTF-8");
        byte[] key1 = DigestUtils.md5Digest(toSign);
        byte[] key2 = appSecureKey.getBytes("UTF-8");
        byte[] key = new byte[key1.length + key2.length];

        System.arraycopy(key1, 0, key, 0, key1.length);
        System.arraycopy(key2, 0, key, key1.length, key2.length);
        return DigestUtils.md5DigestAsHex(key).toLowerCase();
    }

    /**
     * 生成用户parameter认证的签名
     * @param appId
     * @param timestamp
     * @param appSecureKey
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String paramSecure(String appId,String timestamp,String appSecureKey) throws UnsupportedEncodingException {
        String toSign="appid="+appId+"&timestamp="+timestamp;
        StringBuilder sb=new StringBuilder(toSign);
        sb.append(new String(Hex.encode(appSecureKey.getBytes("UTF-8"))));
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8"));
    }
}
