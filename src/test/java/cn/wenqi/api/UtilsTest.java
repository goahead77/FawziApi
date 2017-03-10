package cn.wenqi.api;

import cn.wenqi.api.utils.Utils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author wenqi
 */
public class UtilsTest {

    @Test
    public void signTest() throws UnsupportedEncodingException {

        String appKey= "test";
        String timestamp=String.valueOf(System.currentTimeMillis());
        String appSecure="test";

        System.out.println("appKey:"+appKey+"\ntimestamp:"+timestamp+"\nappSecure:"+appSecure);

        String headSign= Utils.headSecure(appKey,timestamp,appSecure);
        System.out.println("生产进行head信息校验的签名："+headSign);

        String paramSign=Utils.paramSecure(appKey,timestamp,appSecure);
        System.out.println("生产进行param信息校验的签名："+paramSign);
    }
}
