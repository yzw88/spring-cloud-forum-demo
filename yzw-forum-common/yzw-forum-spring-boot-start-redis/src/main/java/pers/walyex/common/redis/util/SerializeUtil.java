package pers.walyex.common.redis.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 序列化工具类
 *
 * @author Waldron Ye
 * @date 2019/5/31 21:20
 */
@Slf4j
public class SerializeUtil {


    /**
     * 序列化
     *
     * @param object
     * @return byte[]
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            log.info("序列化异常:msg=[{}]", e);
        } finally {
            closeOutputStream(oos);
            closeOutputStream(baos);
        }
        return null;
    }

    private static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeIutputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return object
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.info("反序列化异常:msg=[{}]", e);
        } finally {
            closeIutputStream(bais);
            closeIutputStream(ois);
        }
        return null;
    }
}
