package utils;



import java.security.MessageDigest;


/**
 * Md5加密方法
 *
 * @author
 */
public class Md5Utils {
    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            LogUtils.e("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            LogUtils.e("not supported charset...{}", e);
            return s;
        }
    }

    public static String encode(String s) {
        try {
            return hash(Base64Class.encode(s));
        } catch (Exception e) {
            LogUtils.e("not supported charset...{}", e);
            return s;
        }
    }
}
