package pers.walyex.demo.socket;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketTest {


    @Test
    public void socketTest() throws IOException {
        String host = "192.168.31.214";
        int port = 8089;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String json = "{\n" +
                "    \"name\":\"BeJson\",\n" +
                "    \"url\":\"http://www.bejson.com\",\n" +
                "    \"page\":88,\n" +
                "    \"isNonProfit\":true,\n" +
                "    \"address\":{\n" +
                "        \"street\":\"科技园路.\",\n" +
                "        \"city\":\"江苏苏州\",\n" +
                "        \"country\":\"中国\"\n" +
                "    },\n" +
                "    \"links\":[\n" +
                "        {\n" +
                "            \"name\":\"Google\",\n" +
                "            \"url\":\"http://www.google.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\":\"Baidu\",\n" +
                "            \"url\":\"http://www.baidu.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\":\"SoSo\",\n" +
                "            \"url\":\"http://www.SoSo.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        outputStream.write(json.getBytes());
        byte[] buff = new byte[1024];
        inputStream.read(buff);
        String buffer = new String(buff, StandardCharsets.UTF_8);
        log.info("buffer:{}", buffer);
    }
}
