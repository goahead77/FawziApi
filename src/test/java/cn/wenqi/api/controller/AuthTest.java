package cn.wenqi.api.controller;

import cn.wenqi.api.config.TestBase;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.DigestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author wenqi
 */
public class AuthTest extends TestBase {

    @Test
    public void test1() throws Exception {
        String appKey = "test";
        String appSec = "test";
        Long random = System.currentTimeMillis();


        byte[] toSign = (appKey + random).getBytes("UTF-8");
        byte[] key1 = DigestUtils.md5Digest(toSign);
        byte[] key2 = appSec.getBytes("UTF-8");
        byte[] key = new byte[key1.length + key2.length];
        System.arraycopy(key1, 0, key, 0, key1.length);
        System.arraycopy(key2, 0, key, key1.length, key2.length);
        String userSec = DigestUtils.md5DigestAsHex(key).toLowerCase();

        MvcResult result = mockMvc.perform(get("/test/key")
                .header("user-key", appKey)
                .header("user-random", random.toString())
                .header("user-secure", userSec))
                .andReturn();
        String back = result.getResponse().getContentAsString();
        System.out.println(back);
    }

}
