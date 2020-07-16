package per.cby.test;

import java.util.Base64;

import per.cby.frame.common.util.JsonUtil;

/**
 * 
 * 
 * @author chenboyang
 * @since 2019年12月9日
 *
 */
public class MainTest {

    public static void main(String[] args) {
        byte[] data = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        String str1 = JsonUtil.toJson(data).replace("\"", "");
        byte[] data1 = Base64.getDecoder().decode(str1);
        String str2 = Base64.getEncoder().encodeToString(data1);
        byte[] data2 = JsonUtil.toObject("\"" + str2 + "\"", byte[].class);
        System.out.println(data2);
    }

}
